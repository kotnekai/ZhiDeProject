package com.zhide.app.view.widget;

import com.zhide.app.view.widget.effect.BaseEffects;
import com.zhide.app.view.widget.effect.FadeIn;
import com.zhide.app.view.widget.effect.FlipH;
import com.zhide.app.view.widget.effect.FlipV;
import com.zhide.app.view.widget.effect.NewsPaper;
import com.zhide.app.view.widget.effect.SideFall;
import com.zhide.app.view.widget.effect.SlideLeft;
import com.zhide.app.view.widget.effect.SlideRight;
import com.zhide.app.view.widget.effect.SlideTop;
import com.zhide.app.view.widget.effect.SlideBottom;
import com.zhide.app.view.widget.effect.Fall;
import com.zhide.app.view.widget.effect.RotateBottom;
import com.zhide.app.view.widget.effect.RotateLeft;
import com.zhide.app.view.widget.effect.Slit;
import com.zhide.app.view.widget.effect.Shake;


/**
 * Created by lee on 2014/7/30.
 */
public enum Effectstype {

    Fadein(FadeIn.class),
    Slideleft(SlideLeft.class),
    Slidetop(SlideTop.class),
    SlideBottom(SlideBottom.class),
    Slideright(SlideRight.class),
    Fall(Fall.class),
    Newspager(NewsPaper.class),
    Fliph(FlipH.class),
    Flipv(FlipV.class),
    RotateBottom(RotateBottom.class),
    RotateLeft(RotateLeft.class),
    Slit(Slit.class),
    Shake(Shake.class),
    Sidefill(SideFall.class);
    private Class<? extends BaseEffects> effectsClazz;

    private Effectstype(Class<? extends BaseEffects> mclass) {
        effectsClazz = mclass;
    }

    public BaseEffects getAnimator() {
        BaseEffects bEffects = null;
        try {
            bEffects = effectsClazz.newInstance();
        } catch (ClassCastException e) {
            throw new Error("Can not init animatorClazz instance");
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            throw new Error("Can not init animatorClazz instance");
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            throw new Error("Can not init animatorClazz instance");
        }
        return bEffects;
    }
}
