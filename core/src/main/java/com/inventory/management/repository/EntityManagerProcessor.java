package com.inventory.management.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class EntityManagerProcessor {

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<?> findProductFromAudit(String date, String productCode) {
        Query query = entityManager.createNativeQuery(
                "select pa.unit_price from product_aud pa " +
                        "         where pa.modified_date <= :date and pa.code = :productCode " +
                        "         order by pa.modified_date desc limit 1");
        List<?> list = query.setParameter("date", date).setParameter("productCode", productCode).getResultList();
        return list.stream().findFirst();
    }

    public Optional<?> findDailyPeriodicPurchaseOrderQuantities(LocalDate date) {
        Query query = entityManager.createNativeQuery(
                "select count(IF((Date(po.created_date) = DATE_ADD(Date(:date), INTERVAL 1 DAY)), poi.id, null)) as next, " +
                        "       count(IF((Date(po.created_date) = Date(:date)), poi.id, null)) as current, " +
                        "       count(IF((Date(po.created_date) = DATE_SUB(Date(:date), INTERVAL 1 DAY)), poi.id, null)) as previous " +
                        "from purchase_order_item poi " +
                        "         Join purchase_order po on poi.purchase_id = po.id ");
        List<?> list = query.setParameter("date", date).getResultList();
        return list.stream().findFirst();
    }

    public Optional<?> findMonthlyPeriodicPurchaseOrderQuantities(LocalDate date) {
        Query query = entityManager.createNativeQuery(
                "select count(IF(((MONTH(po.created_date) = MONTH(DATE_ADD(Date(:date), INTERVAL 1 MONTH ))) and (YEAR(po.created_date) = YEAR(DATE_ADD(Date(:date), INTERVAL 1 MONTH )))), poi.id, null)) as next, " +
                        "       count(IF(((MONTH(po.created_date) = MONTH(Date(:date))) AND (YEAR(po.created_date) = YEAR(Date(:date)))), poi.id, null)) as current, " +
                        "       count(IF((MONTH(po.created_date) = MONTH(DATE_SUB(Date(:date), INTERVAL 1 MONTH ))) and (YEAR(po.created_date) = YEAR(DATE_SUB(Date(:date), INTERVAL 1 MONTH ))), poi.id, null)) as previous " +
                        "from purchase_order_item poi " +
                        "         Join purchase_order po on poi.purchase_id = po.id");
        List<?> list = query.setParameter("date", date).getResultList();
        return list.stream().findFirst();
    }

    public Optional<?> findYearlyPeriodicPurchaseOrderQuantities(LocalDate date) {
        Query query = entityManager.createNativeQuery(
                "select count(IF(((YEAR(po.created_date) = YEAR(DATE_ADD(Date(:date), INTERVAL 1 YEAR )))), poi.id, null)) as next, " +
                        "       count(IF(((YEAR(po.created_date) = YEAR(Date(:date)))), poi.id, null)) as current, " +
                        "       count(IF((YEAR(po.created_date) = YEAR(DATE_SUB(Date(:date), INTERVAL 1 YEAR ))), poi.id, null)) as previous " +
                        "from purchase_order_item poi " +
                        "         Join purchase_order po on poi.purchase_id = po.id");
        List<?> list = query.setParameter("date", date).getResultList();
        return list.stream().findFirst();
    }

    public Optional<?> findWeeklyPeriodicStatusPurchaseOrderQuantities(LocalDate date) {
        Query query = entityManager.createNativeQuery(
                "select count(IF((Date(po.created_date) = Date(:date)) and po.status = 'Paid', poi.id, null)) as paid, " +
                        "       count(IF((Date(po.created_date) = Date(:date)) and po.status = 'Pending', poi.id, null)) as pending, " +
                        "       count(IF((Date(po.created_date) = Date(:date)), poi.id, null)) as total " +
                        "from purchase_order_item poi " +
                        "         Join purchase_order po on poi.purchase_id = po.id ");
        List<?> list = query.setParameter("date", date).getResultList();
        return list.stream().findFirst();
    }

    public Optional<?> findMonthlyPeriodicStatusPurchaseOrderQuantities(LocalDate date) {
        Query query = entityManager.createNativeQuery(
                "select count(IF(((MONTH(po.created_date) = MONTH(Date(:date))) and po.status = 'Paid' and (YEAR(po.created_date) = YEAR(Date(:date))) ), poi.id, null)) as paid, " +
                        "       count(IF(((MONTH(po.created_date) = MONTH(Date(:date))) and po.status = 'Pending' AND (YEAR(po.created_date) = YEAR(Date(:date)))), poi.id, null)) as pending, " +
                        "       count(IF((MONTH(po.created_date) = MONTH(Date(:date))) and (YEAR(po.created_date) = YEAR(Date(:date))), poi.id, null)) as total " +
                        "from purchase_order_item poi " +
                        "         Join purchase_order po on poi.purchase_id = po.id");
        List<?> list = query.setParameter("date", date).getResultList();
        return list.stream().findFirst();
    }

    public Optional<?> findYearlyPeriodicStatusPurchaseOrderQuantities(LocalDate date) {
        Query query = entityManager.createNativeQuery(
                "select count(IF(((YEAR(po.created_date) = YEAR(Date(:date)))) and po.status = 'Paid', poi.id, null)) as paid, " +
                        "       count(IF(((YEAR(po.created_date) = YEAR(Date(:date)))) and po.status = 'Pending', poi.id, null)) as pending, " +
                        "       count(IF((YEAR(po.created_date) = YEAR(Date(:date))), poi.id, null)) as total " +
                        "from purchase_order_item poi " +
                        "         Join purchase_order po on poi.purchase_id = po.id");
        List<?> list = query.setParameter("date", date).getResultList();
        return list.stream().findFirst();
    }

    public Optional<?> findWeeklyPeriodicStatusProductQuantities(LocalDate date) {
        Query query = entityManager.createNativeQuery(
                "select count(IF((Date(po.created_date) = Date(:date)) and po.status = 'Active', po.id, null)) as active, " +
                        "       count(IF((Date(po.created_date) = Date(:date)) and po.status = 'Inactive', po.id, null)) as inactive, " +
                        "       count(IF((Date(po.created_date) = Date(:date)), po.id, null)) as total " +
                        "from product po ");
        List<?> list = query.setParameter("date", date).getResultList();
        return list.stream().findFirst();
    }

    public Optional<?> findMonthlyPeriodicStatusProductQuantities(LocalDate date) {
        Query query = entityManager.createNativeQuery(
                "select count(IF(((MONTH(po.created_date) = MONTH(Date(:date))) and po.status = 'Active' and (YEAR(po.created_date) = YEAR(Date(:date))) ), po.id, null)) as paid, " +
                        "       count(IF(((MONTH(po.created_date) = MONTH(Date(:date))) and po.status = 'Inactive' AND (YEAR(po.created_date) = YEAR(Date(:date)))), po.id, null)) as pending, " +
                        "       count(IF((MONTH(po.created_date) = MONTH(Date(:date))) and (YEAR(po.created_date) = YEAR(Date(:date))), po.id, null)) as total " +
                        "from product po ");
        List<?> list = query.setParameter("date", date).getResultList();
        return list.stream().findFirst();
    }

    public Optional<?> findYearlyPeriodicStatusProductQuantities(LocalDate date) {
        Query query = entityManager.createNativeQuery(
                "select count(IF(((YEAR(po.created_date) = YEAR(Date(:date)))) and po.status = 'Active', po.id, null)) as paid, " +
                        "       count(IF(((YEAR(po.created_date) = YEAR(Date(:date)))) and po.status = 'Inactive', po.id, null)) as pending, " +
                        "       count(IF((YEAR(po.created_date) = YEAR(Date(:date))), po.id, null)) as total " +
                        "from product po ");
        List<?> list = query.setParameter("date", date).getResultList();
        return list.stream().findFirst();
    }

    public Optional<?> findSummaryCardProductQuantities() {
        Query query = entityManager.createNativeQuery(
                "select count(IF((Date(po.created_date) = Date(CURDATE())) , po.id, null)) as today, " +
                        "       count(IF((Date(po.created_date) = DATE_SUB(Date(curdate()), INTERVAL 1 DAY)) , po.id, null)) as yesterday, " +
                        "       count(IF((MONTH(po.created_date) = MONTH(CURDATE())) and (YEAR(po.created_date) = YEAR(curdate())) and  (WEEK(po.created_date) = WEEK(DATE_SUB(Date(curdate()), INTERVAL 1 WEEK ))), po.id, null)) as lastweek, " +
                        "       count(IF((MONTH(po.created_date) = MONTH(DATE_SUB(Date(curdate()), INTERVAL 1 MONTH ))) and (YEAR(po.created_date) = YEAR(curdate())), po.id, null)) as lastmonth, " +
                        "       count(IF((YEAR(po.created_date) = YEAR(DATE_SUB(Date(curdate()), INTERVAL 1 YEAR ))), po.id, null)) as lastyear " +
                        "from product po ");
        List<?> list = query.getResultList();
        return list.stream().findFirst();
    }

    public Optional<?> findSummaryCardOrderQuantities() {
        Query query = entityManager.createNativeQuery(
                "select count(IF((Date(po.created_date) = Date(CURDATE())) , poi.id, null)) as today, " +
                        "       count(IF((Date(po.created_date) = DATE_SUB(Date(curdate()), INTERVAL 1 DAY)) , poi.id, null)) as yesterday, " +
                        "       count(IF((MONTH(po.created_date) = MONTH(CURDATE())) and (YEAR(po.created_date) = YEAR(curdate())) and  (WEEK(po.created_date) = WEEK(DATE_SUB(Date(curdate()), INTERVAL 1 WEEK ))), poi.id, null)) as lastweek, " +
                        "       count(IF((MONTH(po.created_date) = MONTH(DATE_SUB(Date(curdate()), INTERVAL 1 MONTH ))) and (YEAR(po.created_date) = YEAR(curdate())), poi.id, null)) as lastmonth, " +
                        "       count(IF((YEAR(po.created_date) = YEAR(DATE_SUB(Date(curdate()), INTERVAL 1 YEAR ))), poi.id, null)) as lastyear " +
                        "from purchase_order_item poi " +
                        "         Join purchase_order po on poi.purchase_id = po.id");
        List<?> list = query.getResultList();
        return list.stream().findFirst();
    }

    public Optional<?> findProgressInfoTodayProfit() {
        Query query = entityManager.createNativeQuery(
                "select coalesce( sum(IF((MONTH(po.created_date) = MONTH(CURDATE()))  and (YEAR(po.created_date) = YEAR(curdate()))  and (WEEK(po.created_date) = WEEK(curdate())) , poi.amount * poi.quantity, 0)), 0.0) as thisweek, " +
                        "    coalesce( sum(IF((MONTH(po.created_date) = MONTH(CURDATE()))  and (YEAR(po.created_date) = YEAR(curdate())) and  (WEEK(po.created_date) = WEEK(DATE_SUB(Date(curdate()), INTERVAL 1 WEEK ))), poi.amount * poi.quantity, 0)), 0.0) as lastweek " +
                        "from purchase_order_item poi " +
                        "         Join purchase_order po on poi.purchase_id = po.id");
        List<?> list = query.getResultList();
        return list.stream().findFirst();
    }

    public Optional<?> findProgressInfoTodayNewOrder() {
        Query query = entityManager.createNativeQuery(
                "select count(IF((MONTH(po.created_date) = MONTH(CURDATE()))  and (YEAR(po.created_date) = YEAR(curdate()))  and (WEEK(po.created_date) = WEEK(curdate()))  , poi.id, null) ) as thisweek, " +
                        "    count(IF((MONTH(po.created_date) = MONTH(CURDATE()))  and (YEAR(po.created_date) = YEAR(curdate())) and  (WEEK(po.created_date) = WEEK(DATE_SUB(Date(curdate()), INTERVAL 1 WEEK ))), poi.id, null)) as lastweek " +
                        "from purchase_order_item poi " +
                        "         Join purchase_order po on poi.purchase_id = po.id");
        List<?> list = query.getResultList();
        return list.stream().findFirst();
    }

    public Optional<?> findProgressInfoTodayNewProduct() {
        Query query = entityManager.createNativeQuery(
                "select count(IF((MONTH(po.created_date) = MONTH(CURDATE()))  and (YEAR(po.created_date) = YEAR(curdate()))  and (WEEK(po.created_date) = WEEK(curdate()))  , po.id, null) ) as thisweek, " +
                        "    count(IF((MONTH(po.created_date) = MONTH(CURDATE()))  and (YEAR(po.created_date) = YEAR(curdate())) and  (WEEK(po.created_date) = WEEK(DATE_SUB(Date(curdate()), INTERVAL 1 WEEK ))), po.id, null)) as lastweek " +
                        "from product po " );
        List<?> list = query.getResultList();
        return list.stream().findFirst();
    }

    public List<?> findMonthlyPageView(LocalDate date) {
        Query query = entityManager.createNativeQuery(
                "select count(IF(( (YEAR(v.created_date) = YEAR(Date(:date))) ), v.id, null)) as paid, MONTH(v.created_date) as month from visitor v group by month, v.user");
        return query.setParameter("date", date).getResultList();
    }

    public Optional<?> findNewVisitorStat() {
        Query query = entityManager.createNativeQuery(
                "select count(IF(( (YEAR(u.created_date) = YEAR(Date(curdate()))) and (MONTH(u.created_date) = MONTH(Date(curdate()))) and (WEEK(u.created_date) = WEEK(Date(curdate()))) ), u.id, null)) as new_users," +
                        " count(u.id) as total " +
                        " from user u ");
        return query.getResultList().stream().findFirst();
    }

}
