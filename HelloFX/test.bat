@echo off
java  --enable-preview --module-path "./lib" --add-modules javafx.base,javafx.controls,javafx.fxml,javafx.graphics,javafx.media,javafx.swing,javafx.web,javafx.swt -jar .\HelloFX.jar -cp "./Hellofx.jar;class" 
```