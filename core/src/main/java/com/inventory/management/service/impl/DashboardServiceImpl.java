package com.inventory.management.service.impl;

import com.inventory.management.domain.Product;
import com.inventory.management.domain.PurchaseOrderItem;
import com.inventory.management.repository.EntityManagerProcessor;
import com.inventory.management.repository.ProductRepository;
import com.inventory.management.repository.PurchaseOrderItemRepository;
import com.inventory.management.repository.VisitorRepository;
import com.inventory.management.service.DashboardService;
import com.inventory.management.vo.dto.dashboard.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@SuppressWarnings({"Duplicates", "unchecked"})
public class DashboardServiceImpl implements DashboardService {

    private static final String PATTERN_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final PurchaseOrderItemRepository purchaseOrderItemRepository;
    private final ProductRepository productRepository;
    private final EntityManagerProcessor entityManagerProcessor;
    private final VisitorRepository visitorRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT).withZone(ZoneId.systemDefault());
    private final DateTimeFormatter profitStatFormatter = DateTimeFormatter.ofPattern("MMM-yyyy").withZone(ZoneId.systemDefault());
    private final DateTimeFormatter deltaStatFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd").withZone(ZoneId.systemDefault());

    @Override
    public ProfitBar getProfitBar() {
        List<PurchaseOrderItem> items = purchaseOrderItemRepository.findAllItems();
        List<BigDecimal> itemPurchases = new ArrayList<>();
        List<BigDecimal> products = new ArrayList<>();
        items.forEach(item -> {
            itemPurchases.add(getTotalAmount(item));
            String date = formatter.format(item.getPurchaseOrder().getModifiedDate());
            Optional<?> optionalValue = entityManagerProcessor.findProductFromAudit(date, item.getProductCode());
            if (optionalValue.isPresent()) products.add((BigDecimal) optionalValue.get());
            else products.add(item.getAmount());
        });
        return new ProfitBar(itemPurchases, products);
    }

    @Override
    public ProfitStatCard getProfitStatCard() {
        List<PurchaseOrderItem> items = purchaseOrderItemRepository.findAllItems();
        YearMonth currentFilter = YearMonth.now();
        YearMonth previousFilter = currentFilter.minusMonths(1);
        double currentResult = items.stream()
                .filter(ex -> YearMonth.from(getUTCDate(ex.getPurchaseOrder().getModifiedDate())).equals(currentFilter))
                .map(this::getTotalAmount)
                .filter(Objects::nonNull)
                .mapToDouble(BigDecimal::doubleValue).sum();
        String currentDate = currentFilter.format(profitStatFormatter);
        double previousResult = items.stream()
                .filter(ex -> YearMonth.from(getUTCDate(ex.getPurchaseOrder().getModifiedDate())).equals(previousFilter))
                .map(this::getTotalAmount)
                .filter(Objects::nonNull)
                .mapToDouble(BigDecimal::doubleValue).sum();
        String previousDate = previousFilter.format(profitStatFormatter);
        List<BigDecimal> total = items.stream().map(this::getTotalAmount).collect(Collectors.toList());
        return new ProfitStatCard(currentDate,currentResult, previousDate, previousResult, total );
    }

    @Override
    public List<ProductEarningPieChart> getProductEarningPieChart() {
        List<ProductEarningPieChart> chart =  productRepository.getAllProductName()
                .stream().map(value -> new ProductEarningPieChart(value, purchaseOrderItemRepository.findAllItemSumForProduct(value)))
                .collect(Collectors.toList());
        double total = chart.stream().mapToDouble(ProductEarningPieChart::getValue).sum();
        return chart.stream().peek(value -> {
            double percent =  total == 0 ? 0 :  (double) Math.round(((100 * value.getValue()) /total) * 100) / 100;
            value.setValue(percent);
        }).collect(Collectors.toList());
    }

    private LocalDate getUTCDate(Instant instant) {
        return LocalDateTime.ofInstant(instant, ZoneOffset.UTC).toLocalDate();
    }

    @Override
    public List<String> getProductList() {
        return productRepository.getAllProductName().stream().map(v -> v.replaceAll("\\s+","")).collect(Collectors.toList());
    }

    @Override
    public Map<String, ProductEarningLiveChart> getProductLiveChart() {
        List<ProductEarningLiveChart> chartList = productRepository.getAllProductName()
                .stream().
                map(productName -> {
                    List<PurchaseOrderItem> items = purchaseOrderItemRepository.findAllProductItem(productName);
                    double dailyAmount = extractDailyIncome(items);
                    ProductEarningLiveChart.Delta delta = extractDelta(items);
                    List<ProductEarningLiveChart.LiveChart> liveCharts = items.stream()
                            .map(this::getLiveChart)
                            .collect(Collectors.toList());
                    return new ProductEarningLiveChart(productName, liveCharts, dailyAmount, delta);
                }).collect(Collectors.toList());
        Map<String, ProductEarningLiveChart> map = new HashMap<>();
        for(ProductEarningLiveChart chart: chartList){
            map.put(chart.getProductName().replaceAll("\\s+",""), chart);
        }
        return map;
    }

    @Override
    public List<PeriodPurchaseCount> getPeriodicCount() {
        return List.of(getWeekPeriodicCount(), getMonthPeriodicCount(), getYearPeriodicCount());
    }

    @Override
    public StatusPeriodOrderCount getOrderStatusPeriodicCount() {
        Map<String, StatusPeriodOrderCount.PeriodLevel> levelMap = new HashMap<>();
        levelMap.put("week", getWeeklyOrderLevel());
        levelMap.put("month", getMonthlyOrderLevel());
        levelMap.put("year", getYearlyOrderLevel());
        return new StatusPeriodOrderCount(levelMap);
    }

    @Override
    public StatusPeriodProductCount getProductStatusPeriodicCount() {
        Map<String, StatusPeriodProductCount.PeriodLevel> levelMap = new HashMap<>();
        levelMap.put("week", getWeeklyProductLevel());
        levelMap.put("month", getMonthlyProductLevel());
        levelMap.put("year", getYearlyProductLevel());
        return new StatusPeriodProductCount(levelMap);
    }

    @Override
    public Map<String, List<SummaryCard>> getProductOrderSummary() {
        return Map.of("product", getProductSummaryCard(), "order", getOrderSummaryCard());
    }
    @Override
    public List<ProgressInfo> getProgressInfo() {
        return List.of(getThisWeekProfit(), getThisWeekOrder(), getThisWeekProduct());
    }

    @Override
    public VisitorAnalytics getVisitorAnalytics() {
        List<Long> uniqueVisitors =  visitorRepository.getUniqueVisitors();
        LocalDate monthDate = Year.now(ZoneId.systemDefault()).atMonth(Month.JANUARY).atDay(1);
        List<?> res = entityManagerProcessor.findMonthlyPageView(monthDate);
        List<VisitorAnalytics.PageView> views = new ArrayList<>();
        long totalUser = 0, newUserPercent = 0;
        if(!CollectionUtils.isEmpty(res)) {
            views = res.stream().map(value -> {
                Object[] values = (Object[]) value;
                long pageView = ((BigInteger)values[0]).longValue();
                int mth = ((Integer)values[1]);
                String text = Month.of( mth).getDisplayName(TextStyle.SHORT, Locale.US);
                return new VisitorAnalytics.PageView(text, pageView);
            }).collect(Collectors.toList());
        }
        Optional<Object[]> visitorStat = (Optional<Object[]>) entityManagerProcessor.findNewVisitorStat();
        if(visitorStat.isPresent()) {
            Object[] values = visitorStat.get();
            long newUser = ((BigInteger)values[0]).longValue();
            long total = ((BigInteger)values[1]).longValue();
            totalUser = total;
            newUserPercent = total == 0? 0 :  (newUser * 100)/total;
        }
        return new VisitorAnalytics(totalUser, newUserPercent, uniqueVisitors, views);
    }

    private ProgressInfo getThisWeekProfit() {
        double thisWeek = 0, lastWeek = 0;
        Optional<Object[]> res = (Optional<Object[]>) entityManagerProcessor.findProgressInfoTodayProfit();
        if(res.isPresent()) {
            Object[] values = res.get();
            thisWeek = ((BigDecimal)values[0]).doubleValue();
            lastWeek = ((BigDecimal)values[1]).doubleValue();
        }
        double value = thisWeek - lastWeek;
        double percent = thisWeek + lastWeek == 0 ? 0 :  (thisWeek * 100)/(thisWeek + lastWeek);
        int progress = (int) (value < 0 ? -1 * percent : percent);
        String description = value < 0 ? "Lower than last week %s%%" : "Better than last week %s%%";
        return new ProgressInfo("This Week Profit", thisWeek, progress, String.format(description, progress));
    }

    private ProgressInfo getThisWeekOrder() {
        long thisWeek = 0, lastWeek = 0;
        Optional<Object[]> res = (Optional<Object[]>) entityManagerProcessor.findProgressInfoTodayNewOrder();
        if(res.isPresent()) {
            Object[] values = res.get();
            thisWeek = ((BigInteger)values[0]).longValue();
            lastWeek = ((BigInteger)values[1]).longValue();
        }
        long value = thisWeek - lastWeek;
        long percent = thisWeek + lastWeek == 0 ? 0 :  (thisWeek * 100)/(thisWeek + lastWeek);
        int progress = (int) (value < 0 ? -1 * percent : percent);
        String description = value < 0 ? "Lower than last week %s%%" : "Better than last week %s%%";
        return new ProgressInfo("This Week Order(s)", thisWeek, progress, String.format(description, progress));
    }

    private ProgressInfo getThisWeekProduct() {
        long thisWeek = 0, lastWeek = 0;
        Optional<Object[]> res = (Optional<Object[]>) entityManagerProcessor.findProgressInfoTodayNewProduct();
        if(res.isPresent()) {
            Object[] values = res.get();
            thisWeek = ((BigInteger)values[0]).longValue();
            lastWeek = ((BigInteger)values[1]).longValue();
        }
        long value = thisWeek - lastWeek;
        long percent = thisWeek + lastWeek == 0 ? 0 :  (thisWeek * 100)/(thisWeek + lastWeek);
        int progress = (int) (value < 0 ? -1 * percent : percent);
        String description = value < 0 ? "Lower than last week %s%%" : "Better than last week %s%%";
        return new ProgressInfo("This Week Product(s)", thisWeek, progress, String.format(description, progress));
    }

    private List<SummaryCard> getProductSummaryCard() {
        long today = 0, yesterday = 0, lastWeek = 0, lastMonth = 0, lastYear = 0;
        Optional<Object[]> res = (Optional<Object[]>) entityManagerProcessor.findSummaryCardProductQuantities();
        if(res.isPresent()) {
            Object[] values = res.get();
            today = ((BigInteger)values[0]).longValue();
            yesterday = ((BigInteger)values[1]).longValue();
            lastWeek = ((BigInteger)values[2]).longValue();
            lastMonth = ((BigInteger)values[3]).longValue();
            lastYear = ((BigInteger)values[4]).longValue();
        }
        return List.of(new SummaryCard("Today", today),
                new SummaryCard("Yesterday", yesterday),
                new SummaryCard("Last Week", lastWeek),
                new SummaryCard("Last Month", lastMonth),
                new SummaryCard("Last Year", lastYear));
    }

    private List<SummaryCard> getOrderSummaryCard() {
        long today = 0, yesterday = 0, lastWeek = 0, lastMonth = 0, lastYear = 0;
        Optional<Object[]> res = (Optional<Object[]>) entityManagerProcessor.findSummaryCardOrderQuantities();
        if(res.isPresent()) {
            Object[] values = res.get();
            today = ((BigInteger)values[0]).longValue();
            yesterday = ((BigInteger)values[1]).longValue();
            lastWeek = ((BigInteger)values[2]).longValue();
            lastMonth = ((BigInteger)values[3]).longValue();
            lastYear = ((BigInteger)values[4]).longValue();
        }
        return List.of(new SummaryCard("Today", today),
                new SummaryCard("Yesterday", yesterday),
                new SummaryCard("Last Week", lastWeek),
                new SummaryCard("Last Month", lastMonth),
                new SummaryCard("Last Year", lastYear));
    }

    private StatusPeriodProductCount.PeriodLevel getWeeklyProductLevel() {
        List<String> period = new ArrayList<>();
        List<Long> paid = new ArrayList<>();
        List<Long> pending = new ArrayList<>();
        List<Long> total = new ArrayList<>();
        boolean isAfter = false;
        for(DayOfWeek day: DayOfWeek.values()) {
            if(!isAfter && LocalDate.now().getDayOfWeek().equals(day)) isAfter = true;
            String text = day.getDisplayName(TextStyle.SHORT, Locale.US);
            LocalDate date;
            if(!isAfter) date = LocalDate.now().with(TemporalAdjusters.previous(day));
            else date = LocalDate.now().with(TemporalAdjusters.nextOrSame(day));
            Optional<Object[]> res = (Optional<Object[]>) entityManagerProcessor.findWeeklyPeriodicStatusProductQuantities(date);
            long paidValue = 0, pendingValue = 0, totalValue = 0;
            if(res.isPresent()) {
                Object[] values = res.get();
                paidValue = ((BigInteger)values[0]).longValue();
                pendingValue = ((BigInteger)values[1]).longValue();
                totalValue = ((BigInteger)values[2]).longValue();
            }
            period.add(text);paid.add(paidValue);pending.add(pendingValue);total.add(totalValue);
        }
        return new StatusPeriodProductCount.PeriodLevel(period, paid, pending, total);
    }

    private StatusPeriodProductCount.PeriodLevel getMonthlyProductLevel() {
        List<String> period = new ArrayList<>();
        List<Long> paid = new ArrayList<>();
        List<Long> pending = new ArrayList<>();
        List<Long> total = new ArrayList<>();
        for(Month month: Month.values()) {
            String text = month.getDisplayName(TextStyle.SHORT, Locale.US);
            LocalDate monthDate = Year.now(ZoneId.systemDefault()).atMonth(month).atDay(1);
            Optional<Object[]> res = (Optional<Object[]>) entityManagerProcessor.findMonthlyPeriodicStatusProductQuantities(monthDate);
            long paidValue = 0, pendingValue = 0, totalValue = 0;
            if(res.isPresent()) {
                Object[] values = res.get();
                paidValue = ((BigInteger)values[0]).longValue();
                pendingValue = ((BigInteger)values[1]).longValue();
                totalValue = ((BigInteger)values[2]).longValue();
            }
            period.add(text);paid.add(paidValue);pending.add(pendingValue);total.add(totalValue);
        }
        return new StatusPeriodProductCount.PeriodLevel(period, paid, pending, total);
    }

    private StatusPeriodProductCount.PeriodLevel getYearlyProductLevel() {
        List<String> period = new ArrayList<>();
        List<Long> paid = new ArrayList<>();
        List<Long> pending = new ArrayList<>();
        List<Long> total = new ArrayList<>();
        Year initial = Year.now().minusYears(5);
        for(int i = 0; i < 10; i++) {
            Year current = initial.plusYears(1);
            String text = String.valueOf(current.getValue());
            LocalDate yearDate = current.atDay(1);
            Optional<Object[]> res = (Optional<Object[]>) entityManagerProcessor.findYearlyPeriodicStatusProductQuantities(yearDate);
            long paidValue = 0, pendingValue = 0, totalValue = 0;
            if(res.isPresent()) {
                Object[] values = res.get();
                paidValue = ((BigInteger)values[0]).longValue();
                pendingValue = ((BigInteger)values[1]).longValue();
                totalValue = ((BigInteger)values[2]).longValue();
            }
            period.add(text);paid.add(paidValue);pending.add(pendingValue);total.add(totalValue);
            initial = current;
        }
        return new StatusPeriodProductCount.PeriodLevel(period, paid, pending, total);
    }

    private StatusPeriodOrderCount.PeriodLevel getWeeklyOrderLevel() {
        List<String> period = new ArrayList<>();
        List<Long> paid = new ArrayList<>();
        List<Long> pending = new ArrayList<>();
        List<Long> total = new ArrayList<>();
        boolean isAfter = false;
        for(DayOfWeek day: DayOfWeek.values()) {
            if(!isAfter && LocalDate.now().getDayOfWeek().equals(day)) isAfter = true;
            String text = day.getDisplayName(TextStyle.SHORT, Locale.US);
            LocalDate date;
            if(!isAfter) date = LocalDate.now().with(TemporalAdjusters.previous(day));
            else date = LocalDate.now().with(TemporalAdjusters.nextOrSame(day));
            Optional<Object[]> res = (Optional<Object[]>) entityManagerProcessor.findWeeklyPeriodicStatusPurchaseOrderQuantities(date);
            long paidValue = 0, pendingValue = 0, totalValue = 0;
            if(res.isPresent()) {
                Object[] values = res.get();
                paidValue = ((BigInteger)values[0]).longValue();
                pendingValue = ((BigInteger)values[1]).longValue();
                totalValue = ((BigInteger)values[2]).longValue();
            }
            period.add(text);paid.add(paidValue);pending.add(pendingValue);total.add(totalValue);
        }
        return new StatusPeriodOrderCount.PeriodLevel(period, paid, pending, total);
    }

    private StatusPeriodOrderCount.PeriodLevel getMonthlyOrderLevel() {
        List<String> period = new ArrayList<>();
        List<Long> paid = new ArrayList<>();
        List<Long> pending = new ArrayList<>();
        List<Long> total = new ArrayList<>();
        for(Month month: Month.values()) {
            String text = month.getDisplayName(TextStyle.SHORT, Locale.US);
            LocalDate monthDate = Year.now(ZoneId.systemDefault()).atMonth(month).atDay(1);
            Optional<Object[]> res = (Optional<Object[]>) entityManagerProcessor.findMonthlyPeriodicStatusPurchaseOrderQuantities(monthDate);
            long paidValue = 0, pendingValue = 0, totalValue = 0;
            if(res.isPresent()) {
                Object[] values = res.get();
                paidValue = ((BigInteger)values[0]).longValue();
                pendingValue = ((BigInteger)values[1]).longValue();
                totalValue = ((BigInteger)values[2]).longValue();
            }
            period.add(text);paid.add(paidValue);pending.add(pendingValue);total.add(totalValue);
        }
        return new StatusPeriodOrderCount.PeriodLevel(period, paid, pending, total);
    }

    private StatusPeriodOrderCount.PeriodLevel getYearlyOrderLevel() {
        List<String> period = new ArrayList<>();
        List<Long> paid = new ArrayList<>();
        List<Long> pending = new ArrayList<>();
        List<Long> total = new ArrayList<>();
        Year initial = Year.now().minusYears(5);
        for(int i = 0; i < 10; i++) {
            Year current = initial.plusYears(1);
            String text = String.valueOf(current.getValue());
            LocalDate yearDate = current.atDay(1);
            Optional<Object[]> res = (Optional<Object[]>) entityManagerProcessor.findYearlyPeriodicStatusPurchaseOrderQuantities(yearDate);
            long paidValue = 0, pendingValue = 0, totalValue = 0;
            if(res.isPresent()) {
                Object[] values = res.get();
                paidValue = ((BigInteger)values[0]).longValue();
                pendingValue = ((BigInteger)values[1]).longValue();
                totalValue = ((BigInteger)values[2]).longValue();
            }
            period.add(text);paid.add(paidValue);pending.add(pendingValue);total.add(totalValue);
            initial = current;
        }
        return new StatusPeriodOrderCount.PeriodLevel(period, paid, pending, total);
    }

    private PeriodPurchaseCount getWeekPeriodicCount() {
        Map<String, PeriodPurchaseCount.PeriodLevel> levelMap = new HashMap<>();
        boolean isForward = false;
        for(DayOfWeek day: DayOfWeek.values()) {
            if(!isForward && LocalDate.now().getDayOfWeek().equals(day)) isForward = true;
            String text = day.getDisplayName(TextStyle.SHORT, Locale.US);
            LocalDate date;
            if(!isForward) date = LocalDate.now().with(TemporalAdjusters.previous(day));
            else date = LocalDate.now().with(TemporalAdjusters.nextOrSame(day));
            Optional<Object[]> res = (Optional<Object[]>) entityManagerProcessor.findDailyPeriodicPurchaseOrderQuantities(date);
            if(res.isPresent()) {
                Object[] values = res.get();
                long next = ((BigInteger)values[0]).longValue();
                long cur = ((BigInteger)values[1]).longValue();
                long prev = ((BigInteger)values[2]).longValue();
                levelMap.put(text, new PeriodPurchaseCount.PeriodLevel(prev, cur, next));
            }
            else levelMap.put(text, new PeriodPurchaseCount.PeriodLevel(0, 0, 0));
        }
        return new PeriodPurchaseCount(levelMap);
    }
    private PeriodPurchaseCount getMonthPeriodicCount() {
        Map<String, PeriodPurchaseCount.PeriodLevel> levelMap = new HashMap<>();
        for(Month month: Month.values()) {
            String text = month.getDisplayName(TextStyle.SHORT, Locale.US);
            LocalDate monthDate = Year.now(ZoneId.systemDefault()).atMonth(month).atDay(1);
            Optional<Object[]> res = (Optional<Object[]>) entityManagerProcessor.findMonthlyPeriodicPurchaseOrderQuantities(monthDate);
            if(res.isPresent()) {
                Object[] values = res.get();
                long next = ((BigInteger)values[0]).longValue();
                long cur = ((BigInteger)values[1]).longValue();
                long prev = ((BigInteger)values[2]).longValue();
                levelMap.put(text, new PeriodPurchaseCount.PeriodLevel(prev, cur, next));
            }
            else levelMap.put(text, new PeriodPurchaseCount.PeriodLevel(0, 0, 0));
        }
        return new PeriodPurchaseCount(levelMap);
    }
    private PeriodPurchaseCount getYearPeriodicCount() {
        Map<String, PeriodPurchaseCount.PeriodLevel> levelMap = new HashMap<>();
        Year initial = Year.now().minusYears(5);
        for(int i = 0; i < 10; i++) {
            Year current = initial.plusYears(1);
            String text = String.valueOf(current.getValue());
            LocalDate yearDate = current.atDay(1);
            Optional<Object[]> res = (Optional<Object[]>) entityManagerProcessor.findYearlyPeriodicPurchaseOrderQuantities(yearDate);
            if(res.isPresent()) {
                Object[] values = res.get();
                long next = ((BigInteger)values[0]).longValue();
                long cur = ((BigInteger)values[1]).longValue();
                long prev = ((BigInteger)values[2]).longValue();
                levelMap.put(text, new PeriodPurchaseCount.PeriodLevel(prev, cur, next));
            }
            else levelMap.put(text, new PeriodPurchaseCount.PeriodLevel(0, 0, 0));
            initial = current;
        }
        return new PeriodPurchaseCount(levelMap);
    }

    private ProductEarningLiveChart.LiveChart getLiveChart(PurchaseOrderItem item) {
        return new ProductEarningLiveChart.LiveChart(List.of(deltaStatFormatter.format(item.getPurchaseOrder().getModifiedDate()), getTotalAmount(item)));
    }

    private double extractDailyIncome(List<PurchaseOrderItem> items) {
        return items.stream()
                .filter(item -> getUTCDate(item.getPurchaseOrder().getCreatedDate()).equals(LocalDate.now()))
                .mapToDouble(item -> getTotalAmount(item).doubleValue()).sum();
    }

    private ProductEarningLiveChart.Delta extractDelta(List<PurchaseOrderItem> items) {
        if(!CollectionUtils.isEmpty(items)) {
            if(items.size() < 2) return new ProductEarningLiveChart.Delta(true, getTotalAmount(items.get(0)).doubleValue());
            else {
                PurchaseOrderItem item1 = items.get(items.size() - 1);
                PurchaseOrderItem item2 = items.get(items.size() - 2);
                double item1Amount = getTotalAmount(item1).doubleValue();
                double item2Amount = getTotalAmount(item2).doubleValue();
                if(item1Amount >= item2Amount) return new ProductEarningLiveChart.Delta(true, item1Amount - item2Amount);
                else return new ProductEarningLiveChart.Delta(false, item2Amount - item1Amount);
            }
        }else{
            return new ProductEarningLiveChart.Delta(true, 0);
        }
    }


    private BigDecimal getTotalAmount(PurchaseOrderItem item) {
        Product product = productRepository.findByCode(item.getProductCode()).orElse(null);
        if(product == null) return BigDecimal.ZERO;
        item.setProductName(product.getName());
        item.setAmount(product.getUnitPrice());
        BigDecimal totalAmount = item.getAmount().multiply(new BigDecimal(item.getQuantity()));
        if (!item.getVatEnabled()) return totalAmount;
        else if (product.getTaxInPercentage() != 0) {
            BigDecimal vatAmount = BigDecimal.valueOf((totalAmount.doubleValue() * product.getTaxInPercentage()) / 100);
            return totalAmount.add(vatAmount);
        } else return totalAmount;
    }
}
