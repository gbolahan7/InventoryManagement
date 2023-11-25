package com.inventory.management.operation.print;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.*;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.inventory.management.vo.problem.CustomApiException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.awt.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@SuppressWarnings("deprecation")
public abstract class PrinterOperation<T> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss").withZone(ZoneId.systemDefault());

    public JasperPrint getReport(Collection<T> elements) {
        try {
            Style headerStyle = createHeaderStyle();
            Style detailTextStyle = createDetailTextStyle();
            Style detailNumberStyle = createDetailNumberStyle();
            DynamicReportBuilder reportBuilder = getReport(headerStyle, detailTextStyle, detailNumberStyle);
            reportBuilder.setTitleStyle(createTitleStyle()).setSubtitleStyle(createSubTitleStyle());
            reportBuilder.setSubtitle(String.format("%s at %s", "Report generated", formatter.format(Instant.now())));
            return DynamicJasperHelper.generateJasperPrint(reportBuilder.build(), new ClassicLayoutManager(), new JRBeanCollectionDataSource(elements));
        } catch (Exception e) {
            throw new CustomApiException("An error has occurred");
        }
    }

    private Style createTitleStyle() {
        StyleBuilder titleStyle = new StyleBuilder(true);
        titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        titleStyle.setFont(new Font(20, null, true));
        return titleStyle.build();
    }

    private Style createSubTitleStyle() {
        StyleBuilder subTitleStyle = new StyleBuilder(true);
        subTitleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        subTitleStyle.setFont(new Font(Font.MEDIUM, null, true));
        return subTitleStyle.build();
    }

    private Style createHeaderStyle() {
        return new StyleBuilder(true)
                .setFont(Font.VERDANA_MEDIUM_BOLD)
                .setBorder(Border.THIN())
                .setBorderBottom(Border.PEN_2_POINT())
                .setBorderColor(Color.BLACK)
                .setBackgroundColor(Color.ORANGE)
                .setTextColor(Color.BLACK)
                .setHorizontalAlign(HorizontalAlign.CENTER)
                .setVerticalAlign(VerticalAlign.MIDDLE)
                .setTransparency(Transparency.OPAQUE)
                .build();
    }

    private Style createDetailTextStyle() {
        return new StyleBuilder(true)
                .setFont(Font.VERDANA_MEDIUM)
                .setBorder(Border.DOTTED())
                .setBorderColor(Color.BLACK)
                .setTextColor(Color.BLACK)
                .setHorizontalAlign(HorizontalAlign.LEFT)
                .setVerticalAlign(VerticalAlign.MIDDLE)
                .setPaddingLeft(5)
                .build();
    }

    private Style createDetailNumberStyle() {
        return new StyleBuilder(true)
                .setFont(Font.VERDANA_MEDIUM)
                .setBorder(Border.DOTTED())
                .setBorderColor(Color.BLACK)
                .setTextColor(Color.BLACK)
                .setHorizontalAlign(HorizontalAlign.RIGHT)
                .setVerticalAlign(VerticalAlign.MIDDLE)
                .setPaddingRight(5)
                .setPattern("#,##0.00")
                .build();
    }

    protected AbstractColumn createColumn(String property, Class<?> type, String title, int width, Style headerStyle, Style detailStyle)
            throws ColumnBuilderException {
        return ColumnBuilder.getNew()
                .setColumnProperty(property, type.getName())
                .setTitle(title)
                .setWidth(width)
                .setStyle(detailStyle)
                .setHeaderStyle(headerStyle)
                .build();
    }

    protected abstract DynamicReportBuilder getReport(Style headerStyle, Style detailTextStyle, Style detailNumStyle);
}
