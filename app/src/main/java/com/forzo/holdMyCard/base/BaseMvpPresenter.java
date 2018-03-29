package com.forzo.holdMyCard.base;

/**
 * Created by Shriram on 3/29/2018.
 */

public interface BaseMvpPresenter<V> {


    /**
     * Called when view attached to presenter
     *
     * @param view
     */
    void attach(V view);

    /**
     * Called when view is detached from presenter
     */
    void detach();

    /**
     * @return true if view is attached to presenter
     */
    boolean isAttached();
}
