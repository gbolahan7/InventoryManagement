package com.inventory.management.operation.core.purchaseOrderItem.report;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.inventory.management.annotation.Operation;
import com.inventory.management.operation.print.PrinterOperation;
import com.inventory.management.vo.dto.ProductDto;
import com.inventory.management.vo.dto.PurchaseOrderItemDto;

import java.math.BigDecimal;

@Operation
public class PurchaseOrderItemReportOperation extends PrinterOperation<PurchaseOrderItemDto> {

    @Override
    protected DynamicReportBuilder getReport(Style headerStyle, Style detailTextStyle, Style detailNumStyle)
            throws ColumnBuilderException {
        DynamicReportBuilder report = new DynamicReportBuilder();

        AbstractColumn column1 = createColumn("id", Long.class, "Id", 5, headerStyle, detailTextStyle);
        AbstractColumn column2 = createColumn("productName", String.class, "Name", 10, headerStyle, detailTextStyle);
        AbstractColumn column3 = createColumn("productCode", String.class, "Code", 10, headerStyle, detailTextStyle);
        AbstractColumn column4 = createColumn("amount", BigDecimal.class, "Price", 10, headerStyle, detailNumStyle);
        AbstractColumn column5 = createColumn("vatEnabled", Boolean.class, "VAT", 10, headerStyle, detailTextStyle);
        AbstractColumn column6 = createColumn("quantity", Long.class, "Quantity", 10, headerStyle, detailNumStyle);
        report.addColumn(column1).addColumn(column2).addColumn(column3).addColumn(column4);
        report.addColumn(column5).addColumn(column6);
        report.setTitle("Purchase Item Report");
        report.setUseFullPageWidth(true);
        return report;
    }
}
