package org.cst.buildingnotice.util;

import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.NiceXWPFDocument;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.HyperLinkTextRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.policy.AbstractRenderPolicy;
import com.deepoove.poi.template.run.RunTemplate;
import com.deepoove.poi.util.StyleUtils;


public class TextRenderPolicy extends AbstractRenderPolicy<Object> {

    public static final String REGEX_LINE_CHARACTOR = "\n";

    @Override
    public void doRender(RunTemplate runTemplate, Object renderData, XWPFTemplate template)
            throws Exception {
        XWPFRun run = runTemplate.getRun();
        Helper.renderTextRun(run, renderData);
    }

    public static class Helper {

        public static void renderTextRun(XWPFRun run, Object renderData) {
            // create hyper link run
            if (renderData instanceof HyperLinkTextRenderData) {
                XWPFRun hyperLinkRun = NiceXWPFDocument.insertNewHyperLinkRun(run,
                        ((HyperLinkTextRenderData) renderData).getUrl());
                run.setText("", 0);
                run = hyperLinkRun;
            }

            // text
            TextRenderData textRenderData = renderData instanceof TextRenderData
                    ? (TextRenderData) renderData
                    : new TextRenderData(renderData.toString());

            String data = null == textRenderData.getText() ? "" : textRenderData.getText();

            StyleUtils.styleRun(run, textRenderData.getStyle());

            String[] split = data.split(REGEX_LINE_CHARACTOR, -1);
            if (null != split && split.length > 0) {
                run.setText(split[0], 0);
                for (int i = 1; i < split.length; i++) {
                    run.addBreak();
                    run.setText(split[i]);
                }
            }
        }

    }

}
