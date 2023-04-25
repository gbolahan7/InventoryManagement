package com.inventory.management.operation.core.product.report;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.inventory.management.annotation.Operation;
import com.inventory.management.operation.print.PrinterOperation;
import com.inventory.management.vo.dto.ProductDto;

import java.math.BigDecimal;

@Operation
public class ProductReportOperation extends PrinterOperation<ProductDto> {

    @Override
    protected DynamicReportBuilder getReport(Style headerStyle, Style detailTextStyle, Style detailNumStyle)
            throws ColumnBuilderException {
        DynamicReportBuilder report = new DynamicReportBuilder();

        AbstractColumn column1 = createColumn("id", Long.class, "Id", 5, headerStyle, detailTextStyle);
        AbstractColumn column2 = createColumn("name", String.class, "Name", 10, headerStyle, detailTextStyle);
        AbstractColumn column3 = createColumn("code", String.class, "Code", 10, headerStyle, detailTextStyle);
        AbstractColumn column4 = createColumn("category", String.class, "Category", 10, headerStyle, detailTextStyle);
        AbstractColumn column5 = createColumn("unit", String.class, "Unit", 10, headerStyle, detailTextStyle);
        AbstractColumn column6 = createColumn("brand", String.class, "Brand", 10, headerStyle, detailTextStyle);
        AbstractColumn column7 = createColumn("unitPrice", BigDecimal.class, "Price", 10, headerStyle, detailNumStyle);
        AbstractColumn column8 = createColumn("taxInPercentage", Double.class, "Tax", 10, headerStyle, detailNumStyle);
        AbstractColumn column9 = createColumn("quantity", Long.class, "Quantity", 10, headerStyle, detailNumStyle);
        AbstractColumn column10 = createColumn("status", String.class, "Status", 10, headerStyle, detailTextStyle);
        report.addColumn(column1).addColumn(column2).addColumn(column3).addColumn(column4);
        report.addColumn(column5).addColumn(column6).addColumn(column7).addColumn(column8);
        report.addColumn(column9).addColumn(column10);
        report.setTitle("Product Report");
        report.setUseFullPageWidth(true);
        return report;
    }
}
