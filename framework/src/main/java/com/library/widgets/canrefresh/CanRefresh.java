package com.library.widgets.canrefresh;

/**
 * Copyright 2016 canyinghao
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public interface CanRefresh {

    /**
     * 重置
     */
    void onReset();

    /**
     * 下拉高度大于头部高度
     */
    void onPrepare();

    /**
     * 放手后
     */
    void onRelease();

    /**
     * 刷新完成
     */
    void onComplete();

    /**
     * 下拉高度与头部高度比例
     */
    void onPositionChange(float currentPercent);

    /**
     * 是下拉还是上拉
     *
     * @param isHeader
     */
    void setIsHeaderOrFooter(boolean isHeader);
}
