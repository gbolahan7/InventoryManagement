package com.inventory.management.operation.core.category.report;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.inventory.management.annotation.Operation;
import com.inventory.management.operation.print.PrinterOperation;
import com.inventory.management.vo.dto.CategoryDto;

import java.time.Instant;

@Operation
public class CategoryReportOperation extends PrinterOperation<CategoryDto> {

    @Override
    protected DynamicReportBuilder getReport(Style headerStyle, Style detailTextStyle, Style detailNumStyle)
            throws ColumnBuilderException {
        DynamicReportBuilder report = new DynamicReportBuilder();

        AbstractColumn column1 = createColumn("id", Long.class, "Id", 5, headerStyle, detailTextStyle);
        AbstractColumn column2 = createColumn("name", String.class, "Name", 10, headerStyle, detailTextStyle);
        AbstractColumn column3 = createColumn("description", String.class, "Description", 50, headerStyle, detailTextStyle);
        AbstractColumn column4 = createColumn("itemsTrans", String.class, "Items", 75, headerStyle, detailTextStyle);
        report.addColumn(column1).addColumn(column2).addColumn(column3).addColumn(column4);
        report.setTitle("Category Report");
        report.setUseFullPageWidth(true);
        return report;
    }
}
