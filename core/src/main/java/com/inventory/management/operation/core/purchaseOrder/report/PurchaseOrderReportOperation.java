package com.inventory.management.operation.core.purchaseOrder.report;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.inventory.management.annotation.Operation;
import com.inventory.management.operation.print.PrinterOperation;
import com.inventory.management.vo.dto.PurchaseOrderDto;
import com.inventory.management.vo.dto.PurchaseOrderItemDto;

import java.math.BigDecimal;

@Operation
public class PurchaseOrderReportOperation extends PrinterOperation<PurchaseOrderDto> {

    @Override
    protected DynamicReportBuilder getReport(Style headerStyle, Style detailTextStyle, Style detailNumStyle)
            throws ColumnBuilderException {
        DynamicReportBuilder report = new DynamicReportBuilder();

        AbstractColumn column1 = createColumn("id", Long.class, "Id", 5, headerStyle, detailTextStyle);
        AbstractColumn column2 = createColumn("formattedPurchasedDate", String.class, "Purchased Date", 20, headerStyle, detailTextStyle);
        AbstractColumn column3 = createColumn("description", String.class, "Description", 30, headerStyle, detailTextStyle);
        AbstractColumn column4 = createColumn("status", String.class, "Status", 10, headerStyle, detailTextStyle);
        AbstractColumn column5 = createColumn("formattedItems", String.class, "Items", 50, headerStyle, detailTextStyle);
        report.addColumn(column1).addColumn(column2).addColumn(column3).addColumn(column4);
        report.addColumn(column5);
        report.setTitle("Purchase Order Report");
        report.setUseFullPageWidth(true);
        return report;
    }
}
