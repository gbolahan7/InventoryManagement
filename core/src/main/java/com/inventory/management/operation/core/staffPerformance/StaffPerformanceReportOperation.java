package com.inventory.management.operation.core.staffPerformance;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.inventory.management.annotation.Operation;
import com.inventory.management.operation.print.PrinterOperation;
import com.inventory.management.vo.dto.StaffPerformanceDto;

@Operation
public class StaffPerformanceReportOperation extends PrinterOperation<StaffPerformanceDto> {

    @Override
    protected DynamicReportBuilder getReport(Style headerStyle, Style detailTextStyle, Style detailNumStyle)
            throws ColumnBuilderException {
        DynamicReportBuilder report = new DynamicReportBuilder();

        AbstractColumn column1 = createColumn("id", Long.class, "Id", 15, headerStyle, detailTextStyle);
        AbstractColumn column2 = createColumn("username", String.class, "Name", 50, headerStyle, detailTextStyle);
        AbstractColumn column3 = createColumn("period", String.class, "Period", 50, headerStyle, detailTextStyle);
        AbstractColumn column4 = createColumn("type", String.class, "Type", 30, headerStyle, detailTextStyle);
        AbstractColumn column5 = createColumn("averagePeriodicPerformance", Double.class, "Performance (%)", 30, headerStyle, detailNumStyle);
        AbstractColumn column6 = createColumn("bonusPoint", Double.class, "Bonus Point", 50, headerStyle, detailTextStyle);
        AbstractColumn column7 = createColumn("isNew", Boolean.class, "New", 30, headerStyle, detailTextStyle);
        report.addColumn(column1).addColumn(column2).addColumn(column3).addColumn(column4).addColumn(column5).addColumn(column6).addColumn(column7);
        report.setTitle("Staff Performance Report");
        report.setUseFullPageWidth(true);
        return report;
    }
}
