package fun.ajing;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import fun.ajing.utils.CommonUtil;
import fun.ajing.utils.Pro2yml;
import fun.ajing.utils.Yml2pro;

public class ConvertAction extends AnAction {

    @Override
    public void update(AnActionEvent e){
        if (e == null) {

        }

        super.update(e);
        Presentation presentation = e.getPresentation();
        String fileType = CommonUtil.getFileType(e, false);
        if (!StringUtil.isEmpty(fileType) && presentation.isEnabled()) {
            e.getPresentation().setEnabledAndVisible("YAML".equals(fileType) || "Properties".equals(fileType));
        } else {
            e.getPresentation().setEnabledAndVisible(false);
        }
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        if (e == null) {

        }

        String fileType = CommonUtil.getFileType(e, true);
        PsiFile selectedFile = CommonUtil.getSelectedFile(e, true);
        if (!StringUtil.isEmpty(fileType) && selectedFile != null) {
            VirtualFile virtualFile = selectedFile.getVirtualFile();
            if (virtualFile != null) {
                String path = virtualFile.getPath();
                int index = path.lastIndexOf(".");
                String suffix = path.substring(index + 1);
                String prefix = path.substring(0, index);
                if ("properties".equals(suffix)) {
                    prefix += ".yml";
                    Pro2yml.convert2YmlFile(path, prefix);
                } else {
                    prefix += ".properties";
                    Yml2pro.convert2PropertiesFile(path, prefix);
                }
                VirtualFile newVirtualFile = LocalFileSystem.getInstance().findFileByPath(prefix);
                if (newVirtualFile != null) {
                    newVirtualFile.refresh(false, true); // 同步刷新新文件
                }
            }
        }

    }
}
