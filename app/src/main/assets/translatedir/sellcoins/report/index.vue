<template>
  <page
    bgColor="#F5F7FA"
    contentBGColor="#F5F7FA"
    :navigation="{ title: $t('merchant_report') }"
  >
    <div class="fill-v-layout">
      <fill-section
        class="fill-section"
        :title="$t('merchant_reason_for_report')"
      >
        <div class="low-sell-h-layout">
          <span class="low-sell-text-view">{{
            $t("merchant_report_reason_low_sell")
          }}</span>
          <van-image
            class="checked-image-view"
            :src="require('@/common/assets/common_checked_ic.png')"
          />
        </div>
      </fill-section>
      <fill-section
        class="fill-section"
        :title="$t('merchant_currency_merchant_id')"
      >
        <input
          v-model="merchantId"
          class="fill-edit-view"
          :placeholder="$t('common_fill_out')"
        />
      </fill-section>
      <fill-section
        class="fill-section"
        :title="$t('merchant_picture_evidence')"
      >
        <upload-file-add-view
          v-model="pictureEvidenceFiles"
          :maxCount="4"
          source="picture-evidence"
        />
      </fill-section>
      <fill-section
        class="fill-section"
        :title="$t('merchant_video_evidence')"
        v-if="showVideoUpload"
      >
        <upload-file-add-view
          v-model="videoEvidenceFiles"
          :maxCount="1"
          :mediaType="1"
          source="video-evidence"
        />
      </fill-section>
      <span
        :class="[
          completeBtnEnable ? 'complete-button' : 'complete-disable-button',
        ]"
        @click="onCompleteClick"
      >
        {{ $t("common_complete") }}
      </span>
      <van-popup v-model="showConfirmReportDialog" get-container="body" round>
        <common-dialog
          :messageText="$t('merchant_confirm_report')"
          :primaryButtonText="$t('common_confirm')"
          :secondaryButtonText="$t('common_cancel')"
          @on-primary-button-click="onConfirmReportClick"
          @on-secondary-button-click="onCancelReportClick"
        >
          <dialog-user-info
            v-if="merchantUserInfo !== null"
            :userInfo="merchantUserInfo"
            :id="merchantId"
          ></dialog-user-info>
        </common-dialog>
      </van-popup>
      <van-overlay :show="showLoading">
        <div class="loading-view" @click.stop>
          <van-loading type="spinner" size="48px" />
        </div>
      </van-overlay>
    </div>
  </page>
</template>
<script>
import _ from "lodash";
import Page from "@/common/components/Page.vue";
import {
  hideNavigationBar,
  getVersionCode,
  android,
} from "../../../common/bridge/index";
import FillSection from "@/common/components/FillSection.vue";
import UploadFileAddView from "@/common/components/UploadFileAddView";
import api from "../api/index";
import Vue from "vue";
import { Image as VanImage, Toast, Loading, Popup, Overlay } from "vant";
import { RES_COMMON_CODE } from "../../../common/network/constant";
import { uploadImgs, uploadVideos } from "../../../common/oss/index";
import CommonDialog from "@/common/components/CommonDialog";
import DialogUserInfo from "@/common/components/DialogUserInfo";
import commonApi from "@/common/api/index";

Vue.use(VanImage);
Vue.use(Toast);
Vue.use(Loading);
Vue.use(Popup);
Vue.use(Overlay);
export default {
  name: "ReportMerchant",
  components: {
    page: Page,
    "fill-section": FillSection,
    "upload-file-add-view": UploadFileAddView,
    "common-dialog": CommonDialog,
    "dialog-user-info": DialogUserInfo,
  },
  created() {
    hideNavigationBar();
    this.checkVersion();
  },
  data() {
    return {
      videoEvidenceFiles: [],
      pictureEvidenceFiles: [],
      merchantId: "",
      merchantUserInfo: null,
      showConfirmReportDialog: false,
      showLoading: false,
      showVideoUpload: false,
    };
  },
  methods: {
    async checkVersion() {
      let isAndroid = await android();
      if (isAndroid) {
        let versionCode = await getVersionCode();
        if (versionCode && versionCode > 109) {
          this.showVideoUpload = true;
        }
      }
    },
    async onCompleteClick() {
      const res = await api.getUserInfoByGoodOrShortId(this.merchantId);
      if (res.code === RES_COMMON_CODE.SUCCESS) {
        if (_.isNil(res.data.currencySeller) || res.data.currencySeller === false) {
          Toast(this.$t("merchant_no_a_merchant"));
          return;
        }
        this.merchantUserInfo = res.data;
        this.showConfirmReportDialog = true;
      } else if (res.code == RES_COMMON_CODE.NO_REGISTER) {
        Toast(this.$t("common_user_no_exist"));
        return;
      } else {
        Toast(this.$t("common_failed_with_errorcode", { code: res.code }));
        this.merchantUserInfo = null;
        return;
      }
    },
    async onConfirmReportClick() {
      this.showConfirmReportDialog = false;
      this.showLoading = true;
      var imageList = [];
      var videoList = [];
      if (!_.isEmpty(this.pictureEvidenceFiles)) {
        const { code, data } = await uploadImgs(this.pictureEvidenceFiles);
        if (code !== RES_COMMON_CODE.SUCCESS || _.isEmpty(data)) {
          Toast(this.$t("common_failed_with_errorcode", { code }));
          this.showLoading = false;
          return;
        }
        data.forEach((result) => {
          const { code, data } = result;
          if (code === RES_COMMON_CODE.SUCCESS) {
            imageList.push(data);
          }
        });
      }
      if (!_.isEmpty(this.videoEvidenceFiles)) {
        const { code, data } = await uploadVideos(this.videoEvidenceFiles);
        if (code !== RES_COMMON_CODE.SUCCESS || _.isEmpty(data)) {
          Toast(this.$t("common_failed_with_errorcode", { code }));
          this.showLoading = false;
          return;
        }
        data.forEach((result) => {
          const { code, data } = result;
          if (code === RES_COMMON_CODE.SUCCESS) {
            videoList.push(data);
          }
        });
      }
      const { code } = await commonApi.reportUser({
        reportedUid: this.merchantUserInfo.uid,
        reportType: 8,
        reason: 8,
        videoList: JSON.stringify(videoList),
        imgList: JSON.stringify(imageList),
      });
      this.showLoading = false;
      if (code === RES_COMMON_CODE.SUCCESS) {
        Toast(this.$t("common_success"));
        this.$router.go(-1);
      } else {
        Toast(this.$t("common_failed_with_errorcode", { code }));
      }
    },
    onCancelReportClick() {
      this.showConfirmReportDialog = false;
    },
  },
  computed: {
    completeBtnEnable() {
      return (
        !_.isEmpty(this.merchantId) &&
        (!_.isEmpty(this.pictureEvidenceFiles) ||
          !_.isEmpty(this.videoEvidenceFiles))
      );
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/text.scss" as text;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include v-layout(fill) {
  width: 100%;
  .fill-section {
    margin-top: 16px;
  }
  @include edit-view(
    fill,
    $width: 100%,
    $height: 98px,
    $font-size: 28px,
    $font-color: $--color-777777,
    $font-weight: $--font-weight-regular,
    $background-color: $--color-F5F7FA
  ) {
    border-radius: 32px;
    padding: 0px 32px;
  }
  @include h-layout(low-sell) {
    width: 100%;
    justify-content: space-between;
    @include text-view(
      low-sell,
      $font-size: 28px,
      $line-height: 34px,
      $font-color: $--color-222222,
      $font-weight: $--font-weight-normal
    );
  }
  @include image-view(checked, 40px, 40px);
  @include button(
    complete,
    $width: 638px,
    $height: 96px,
    $text-size: 32px,
    $text-color: $--color-white,
    $border-radius: 48px,
    $background: $--color-00E0D7
  ) {
    margin-top: 40px;
    margin-bottom: 80px;
  }
  .complete-disable-button {
    @extend .complete-button;
    opacity: 0.5;
    pointer-events: none;
  }
  .loading-view {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100%;
  }
}
</style>
