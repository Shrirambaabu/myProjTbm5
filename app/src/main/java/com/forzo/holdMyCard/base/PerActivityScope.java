package com.forzo.holdMyCard.base;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Shriram on 3/29/2018.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivityScope {
}
