package com.example.branchiodemo

import android.app.Activity
import android.content.Context
import androidx.core.content.ContextCompat
import io.branch.indexing.BranchUniversalObject
import io.branch.referral.Branch
import io.branch.referral.BranchError
import io.branch.referral.SharingHelper
import io.branch.referral.util.*
import java.util.*

object BranchUtils {

    private val TAG: String = this::class.java.simpleName

    @JvmStatic
    fun init(applicationContent: Context) {
        GlobalLoggerUtils.showLog(TAG, "BuildConfig.DEBUG: ${BuildConfig.DEBUG}")

        // Branch logging for debugging
        @Suppress("ConstantConditionIf")
        if (BuildConfig.DEBUG) {
            Branch.enableTestMode()
            Branch.enableLogging()

        }

        // Initialize the Branch object
        Branch.setPlayStoreReferrerCheckTimeout(0)
        Branch.getAutoInstance(applicationContent)

    }

    @JvmStatic
    fun generate(context: Context) {
        GlobalLoggerUtils.showLog(TAG, "generate()")

        // https://blog.branch.io/branch-concepts-the-branch-universal-object/

        val buo = BranchUniversalObject()
            .setCanonicalIdentifier("content/12345")

            // for social medium....
            .setTitle("My Content Title")
            .setContentDescription("My Content Description")
            .setContentImageUrl("https://lorempixel.com/400/400")

            .setContentIndexingMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)
            .setLocalIndexMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)
            .setContentMetadata(
                ContentMetadata().addCustomMetadata("key1", "value1")
            )

        val lp = LinkProperties()
            .setChannel("facebook")
            .setFeature("sharing")
            .setCampaign("content 123 launch")
            .setStage("new user")
            .addControlParameter(BranchConstants.DESKTOP_URL, "http://example.com/home")
            .addControlParameter("custom_random", Calendar.getInstance().time.toString())

        buo.generateShortUrl(context, lp) { url, error ->
            GlobalLoggerUtils.showLog(TAG, "generateShortUrl( >>> error: $error, url: $url ")
        }

        val ss = ShareSheetStyle(context, "Check this out!", "This stuff is awesome: ")
            .setCopyUrlStyle(
                ContextCompat.getDrawable(context, android.R.drawable.ic_menu_send),
                "Copy",
                "Added to clipboard"
            )
            .setMoreOptionStyle(
                ContextCompat.getDrawable(context, android.R.drawable.ic_menu_search),
                "Show more"
            )
            .addPreferredSharingOption(SharingHelper.SHARE_WITH.FACEBOOK)
            .addPreferredSharingOption(SharingHelper.SHARE_WITH.EMAIL)
            .addPreferredSharingOption(SharingHelper.SHARE_WITH.MESSAGE)
            .addPreferredSharingOption(SharingHelper.SHARE_WITH.HANGOUT)
            .addPreferredSharingOption(SharingHelper.SHARE_WITH.HANGOUT)
            .setAsFullWidthStyle(true)
            .setSharingTitle("Share With")

        buo.showShareSheet(context as Activity, lp, ss, object : Branch.BranchLinkShareListener {
            override fun onShareLinkDialogLaunched() {
                GlobalLoggerUtils.showLog(TAG, "onShareLinkDialogLaunched()")
            }

            override fun onShareLinkDialogDismissed() {
                GlobalLoggerUtils.showLog(TAG, "onShareLinkDialogDismissed()")
            }

            override fun onLinkShareResponse(sharedLink: String, sharedChannel: String, error: BranchError) {
                GlobalLoggerUtils.showLog(
                    TAG,
                    "onLinkShareResponse() >>> sharedLink: $sharedLink, sharedChannel: $sharedChannel, error: $error"
                )
            }

            override fun onChannelSelected(channelName: String) {
                GlobalLoggerUtils.showLog(TAG, "onChannelSelected() >>> channelName: $channelName")
            }
        })

        BranchEvent(BRANCH_STANDARD_EVENT.SHARE).addContentItems(buo).logEvent(context)
    }
}