package com.inventory.management.service.impl;

import com.inventory.management.mapper.*;
import com.inventory.management.operation.core.category.list.ListCategoryOperation;
import com.inventory.management.operation.core.category.report.CategoryReportOperation;
import com.inventory.management.operation.core.product.list.ListProductOperation;
import com.inventory.management.operation.core.product.report.ProductReportOperation;
import com.inventory.management.operation.core.purchaseOrder.list.ListPurchaseOrderOperation;
import com.inventory.management.operation.core.purchaseOrder.report.PurchaseOrderReportOperation;
import com.inventory.management.operation.core.purchaseOrderItem.list.ListPurchaseOrderItemOperation;
import com.inventory.management.operation.core.purchaseOrderItem.report.PurchaseOrderItemReportOperation;
import com.inventory.management.operation.core.staffPerformance.StaffPerformanceReportOperation;
import com.inventory.management.operation.core.unit.list.ListUnitOperation;
import com.inventory.management.operation.core.unit.report.UnitReportOperation;
import com.inventory.management.operation.list.ListOperationRequest;
import com.inventory.management.service.ReportService;
import com.inventory.management.service.StaffPerformanceService;
import com.inventory.management.vo.dto.*;
import com.inventory.management.vo.problem.CustomApiException;
import com.inventory.management.vo.request.PageRequest;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final CategoryReportOperation categoryReportOperation;
    private final CategoryMapper categoryMapper;
    private final ListCategoryOperation listCategoryOperation;
    private final ProductReportOperation productReportOperation;
    private final ProductMapper productMapper;
    private final ListProductOperation listProductOperation;
    private final PurchaseOrderItemReportOperation purchaseOrderItemReportOperation;
    private final PurchaseOrderItemMapper purchaseOrderItemMapper;
    private final ListPurchaseOrderItemOperation listPurchaseOrderItemOperation;
    private final PurchaseOrderReportOperation purchaseOrderReportOperation;
    private final PurchaseOrderMapper purchaseOrderMapper;
    private final ListPurchaseOrderOperation listPurchaseOrderOperation;
    private final UnitReportOperation unitReportOperation;
    private final UnitMapper unitMapper;
    private final ListUnitOperation listUnitOperation;
    private final StaffPerformanceReportOperation staffPerformanceReportOperation;
    private final StaffPerformanceService staffPerformanceService;
    private final Map<String, Function<JasperPrint, byte[]>> exporterMap = Map.of(
            "pdf", this::getReportPdf,
            "xlsx", this::getReportXlsx
    );

    @Override
    public byte[] getCategories(PageRequest pageRequest, String format, Map<String, Object> filter) {
        List<CategoryDto> list = listCategoryOperation.process(new ListOperationRequest(pageRequest, filter)).getPage()
                .map(categoryMapper::toDto).toList();
        return exporterMap.getOrDefault(format, jasperPrint -> new byte[0]).apply(categoryReportOperation.getReport(list));
    }

    @Override
    public byte[] getProducts(PageRequest pageRequest, String format, Map<String, Object> filter) {
        List<ProductDto> list = listProductOperation.process(new ListOperationRequest(pageRequest, filter)).getPage()
                .map(productMapper::toDto).toList();
        return exporterMap.getOrDefault(format, jasperPrint -> new byte[0]).apply(productReportOperation.getReport(list));
    }

    @Override
    public byte[] getPurchaseOrderItems(PageRequest pageRequest, String format, Map<String, Object> filter) {
        List<PurchaseOrderItemDto> list = listPurchaseOrderItemOperation.process(new ListOperationRequest(pageRequest, filter)).getPage()
                .map(purchaseOrderItemMapper::toDto).toList();
        return exporterMap.getOrDefault(format, jasperPrint -> new byte[0]).apply(purchaseOrderItemReportOperation.getReport(list));
    }

    @Override
    public byte[] getPurchaseOrders(PageRequest pageRequest, String format, Map<String, Object> filter) {
        List<PurchaseOrderDto> list = listPurchaseOrderOperation.process(new ListOperationRequest(pageRequest, filter)).getPage()
                .map(purchaseOrderMapper::toDto).toList();
        return exporterMap.getOrDefault(format, jasperPrint -> new byte[0]).apply(purchaseOrderReportOperation.getReport(list));
    }

    @Override
    public byte[] getUnits(PageRequest pageRequest, String format, Map<String, Object> filter) {
        List<UnitDto> list = listUnitOperation.process(new ListOperationRequest(pageRequest, filter)).getPage()
                .map(unitMapper::toDto).toList();
        return exporterMap.getOrDefault(format, jasperPrint -> new byte[0]).apply(unitReportOperation.getReport(list));
    }
    @Override
    public byte[] getStaffPerformances(String format) {
        List<StaffPerformanceDto> staff = staffPerformanceService.getAllStaffPerformance();
        return exporterMap.getOrDefault(format, jasperPrint -> new byte[0]).apply(staffPerformanceReportOperation.getReport(staff));
    }

    private byte[] getReportPdf(final JasperPrint jasperPrint) {
        try {
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException e) {
            throw new CustomApiException("");
        }
    }

    private byte[] getReportXlsx(final JasperPrint jasperPrint) {
        final JRXlsxExporter xlsxExporter = new JRXlsxExporter();
        final byte[] rawBytes;

        try (final ByteArrayOutputStream xlsReport = new ByteArrayOutputStream()) {
            xlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsReport));
            xlsxExporter.exportReport();

            rawBytes = xlsReport.toByteArray();
        } catch (Exception e) {
            throw new CustomApiException("");
        }
        return rawBytes;
    }
}
