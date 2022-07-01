package com.qwuiteam.project.view

/**
 *
 * Description:
 * Author: zw
 * CreateDate: 2021/11/6 14:26
 */
interface IPKView {

    fun update(myScore: Float, otherScore: Float)

    fun playAnimation(play: Boolean)

    fun reset()

    fun setTextSize(size: Int)
    
    fun setTestMargin(value:Int)
}