package com.example.mysampleproject


import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Path
import android.os.Bundle
import android.view.View
import android.view.animation.PathInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import kotlinx.android.synthetic.main.activity_morphing.*

/**
 * Created by laetitia on 9/9/15.
 */
class Morphing1 : AppCompatActivity() {

    private val ANIMATION_INTERVAL_MS = 600
    private val ANIMATION_DURATION_MS = 700


    internal var mOrigSize: Int = 0
    internal var mOrigRadius: Int = 0
    internal var mTargetRadius1: Int = 0
    internal var mTargetRadius2: Int = 0
    internal var mTargetRadius3: Int = 0

    internal var mTargetSize1: Int = 0
    internal var mTargetSize2: Int = 0

    val strings = mutableListOf<String>()
    internal lateinit var toSmallSquare: ValueAnimator;
    internal lateinit var toSmallRectangle:ValueAnimator;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_morphing)


        mOrigSize = resources.getDimensionPixelSize(R.dimen.morphing_original_size)
        mOrigRadius = resources.getDimensionPixelSize(R.dimen.morphing_original_radius)

        mTargetSize1 = mOrigSize * 2
        mTargetSize2 = mOrigSize * 4

        mTargetRadius1 = resources.getDimensionPixelSize(R.dimen.morphing_target_radius_1)
        mTargetRadius2 = mOrigRadius * 4
        mTargetRadius3 = resources.getDimensionPixelSize(R.dimen.morphing_target_radius_2)

        val path = Path()
        path.arcTo(0f, 0f, 1000f, 1000f, 270f, -180f, true)


        clickme.setOnClickListener { doMaterialAnimation() }

        clickme1.setOnClickListener {
            if (strings.size == 0) {
                doMaterialAnimation1()
                clickme1.isEnabled = false
            }
            if (strings.size == 1) {
                doMaterialAnimation2()
                clickme1.isEnabled = false

            }
            if (strings.size == 2) {
                doMaterialAnimation3()
                clickme1.isEnabled = false

            }
        }

    }

    private fun doMaterialAnimation1() {

        val path = Path()
        path.setLastPoint(img.x, img.y)
        path.lineTo(450f, 800f)
        val animator = ObjectAnimator.ofFloat(img, View.X, View.Y, path)
        animator.duration = 2000
        val alpha1 = ObjectAnimator.ofFloat(img, "alpha", 0f)
        alpha1.setDuration(100)

        val alpha2 = ObjectAnimator.ofFloat(img, "alpha", 0f)
        alpha2.setDuration(2000)
        val animset = AnimatorSet()
        animset.play(alpha2).before(alpha1).with(animator)
        animset.start()

        animset.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {

            }

            override fun onAnimationEnd(animator: Animator) {
                strings.add("1")
                v1.setBackgroundColor(resources.getColor(R.color.colorAccent))
                img.visibility = View.GONE
                clickme1.isEnabled = true


            }

            override fun onAnimationCancel(animator: Animator) {

            }

            override fun onAnimationRepeat(animator: Animator) {}
        })

    }

    private fun doMaterialAnimation2() {
        val path = Path()
        path.setLastPoint(img1.x, img1.y)
        path.lineTo(400f, 800f)
        val animator = ObjectAnimator.ofFloat(img1, View.X, View.Y, path)
        animator.duration = 2000
        val alpha1 = ObjectAnimator.ofFloat(img1, "alpha", 0f)
        alpha1.setDuration(100)

        val alpha2 = ObjectAnimator.ofFloat(img1, "alpha", 0f)
        alpha2.setDuration(2000)
        val animset = AnimatorSet()
        animset.play(alpha2).before(alpha1).with(animator)
        animset.start()

        animset.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {

            }

            override fun onAnimationEnd(animator: Animator) {

                strings.add("2")
                v2.setBackgroundColor(resources.getColor(R.color.colorPrimaryDark))
                img1.visibility = View.GONE
                clickme1.isEnabled = true

            }

            override fun onAnimationCancel(animator: Animator) {

            }

            override fun onAnimationRepeat(animator: Animator) {}
        })


    }

    private fun doMaterialAnimation3() {
        val path = Path()
        path.setLastPoint(img2.x, img2.y)
        path.lineTo(400f, 800f)
        val animator = ObjectAnimator.ofFloat(img2, View.X, View.Y, path)
        animator.duration = 2000
        val alpha1 = ObjectAnimator.ofFloat(img2, "alpha", 0f)
        alpha1.setDuration(100)
        val alpha2 = ObjectAnimator.ofFloat(img2, "alpha", 0f)
        alpha2.setDuration(2000)
        val animset = AnimatorSet()
        animset.play(alpha2).before(alpha1).with(animator)
        animset.start()

        animset.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {

            }

            override fun onAnimationEnd(animator: Animator) {

                strings.add("3")
                v3.setBackgroundColor(resources.getColor(R.color.dot_dark_screen1))
                img2.visibility = View.GONE
                clickme1.visibility = View.GONE
                clickme.visibility = View.VISIBLE

            }

            override fun onAnimationCancel(animator: Animator) {

            }

            override fun onAnimationRepeat(animator: Animator) {}
        })


    }


    private fun doMaterialAnimation() {

        if (strings.size <= 0) {
            img.visibility = View.VISIBLE
            img1.visibility = View.VISIBLE
            img2.visibility = View.VISIBLE

            // From circle to small square
            toSmallSquare = ObjectAnimator.ofFloat(1f, 0f) // A set of values that the animation will animate between over time.
            toSmallSquare.addUpdateListener { animation ->
                //  Redraw the cardview on each update

                transformMaterial(mOrigSize, mTargetSize1, mOrigSize, mTargetSize1, mTargetRadius1, mTargetRadius2, animation)
            }


            val animatorSet = AnimatorSet()
            animatorSet.playSequentially(toSmallSquare)
            animatorSet.duration = ANIMATION_DURATION_MS.toLong()
            animatorSet.interpolator = PathInterpolator(0.75f, 0f, 0.25f, 1f) // see http://cubic-bezier.com/#.75,0,.25,1
            animatorSet.start()
            animatorSet.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animator: Animator) {


                }

                override fun onAnimationEnd(animator: Animator) {

                    clickme.visibility = View.GONE
                    clickme1.visibility = View.VISIBLE

                }

                override fun onAnimationCancel(animator: Animator) {

                }

                override fun onAnimationRepeat(animator: Animator) {

                }
            })

        } else {

            toSmallRectangle = ObjectAnimator.ofFloat(1f, 0f) // A set of values that the animation will animate between over time.
            toSmallRectangle.startDelay = ANIMATION_INTERVAL_MS.toLong()
            toSmallRectangle.addUpdateListener { animation ->
                transformMaterial(mTargetSize1, mOrigSize, mTargetSize1, mOrigSize, mTargetRadius2, mTargetRadius1, animation)

            }

            val animatorSet = AnimatorSet()
            animatorSet.playSequentially(toSmallRectangle)
            animatorSet.duration = ANIMATION_DURATION_MS.toLong()
            animatorSet.interpolator = PathInterpolator(0.75f, 0f, 0.25f, 1f) // see http://cubic-bezier.com/#.75,0,.25,1
            animatorSet.start()

            animatorSet.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animator: Animator) {


                }

                override fun onAnimationEnd(animator: Animator) {

                    lin1.visibility = View.GONE
                    txt1.visibility = View.VISIBLE
                    clickme.visibility = View.GONE

                    val path = Path()
                    path.setLastPoint(card.x, card.y)
                    path.lineTo(card.x, 200f)
                    val animator1 = ObjectAnimator.ofFloat(card, View.X, View.Y, path)
                    animator1.duration = 2000
                    animator1.start()



                }

                override fun onAnimationCancel(animator: Animator) {

                }

                override fun onAnimationRepeat(animator: Animator) {

                }
            })

        }


    }

    private fun transformMaterial(origWidth: Int,
                                  targetWidth: Int,
                                  origHeight: Int,
                                  targetHeight: Int,
                                  origRadius: Int,
                                  targetRadius: Int,
                                  animation: ValueAnimator) {

        val fraction = animation.animatedValue as Float
        card.radius = interpolate(origRadius, targetRadius, fraction)

        if (origWidth != targetWidth) {
            card.layoutParams.width = ((targetWidth - origWidth) * (1 - fraction) + origWidth).toInt()
        }
        if (origHeight != targetHeight) {
            card.layoutParams.height = ((targetHeight - origHeight) * (1 - fraction) + origWidth).toInt()
        }

        card.requestLayout()
    }

    private fun interpolate(from: Int, to: Int, fraction: Float): Float {
        return (from - to) * fraction + to
    }

}
