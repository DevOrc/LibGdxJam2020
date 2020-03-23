package com.devorc.gdxjam.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.devorc.gdxjam.Game;
import com.google.gwt.user.client.Window;

public class HtmlLauncher extends GwtApplication {

        // USE THIS CODE FOR A FIXED SIZE APPLICATION
        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(800, 600);
        }
        // END CODE FOR FIXED SIZE APPLICATION

        // UNCOMMENT THIS CODE FOR A RESIZABLE APPLICATION
//         PADDING is to avoid scrolling in iframes, set to 20 if you have problems
//         private static final int PADDING = 20;
//         private GwtApplicationConfiguration cfg;
//
//         @Override
//         public GwtApplicationConfiguration getConfig() {
//             int w = Window.getClientWidth() - PADDING;
//             int h = Window.getClientHeight() - PADDING;
//             cfg = new GwtApplicationConfiguration(w, h);
//             Window.enableScrolling(false);
//             Window.setMargin("0");
//             Window.addResizeHandler(new ResizeListener());
//             cfg.preferFlash = false;
//             return cfg;
//         }
//
//         class ResizeListener implements ResizeHandler {
//             @Override
//             public void onResize(ResizeEvent event) {
//                 int width = event.getWidth() - PADDING - 100;
//                 int height = event.getHeight() - PADDING;
//                 getRootPanel().setWidth("" + width + "px");
//                 getRootPanel().setHeight("" + height + "px");
//                 getApplicationListener().resize(width, height);
//                 Gdx.graphics.setWindowedMode(width, height);
//             }
//         }
        // END OF CODE FOR RESIZABLE APPLICATION

        @Override
        public ApplicationListener createApplicationListener () {
                return new Game();
        }

        @Override
        public void exit() {
                Window.alert("To quit, you must close the tab!");
                super.exit();
        }
}