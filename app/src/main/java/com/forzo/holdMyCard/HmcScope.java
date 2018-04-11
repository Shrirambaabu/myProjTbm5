package com.forzo.holdMyCard;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Shriram on 4/11/2018.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface HmcScope {
}
