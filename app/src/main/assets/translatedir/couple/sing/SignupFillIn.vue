<template>
  <div class="we-page">
    <div class="content-bg"></div>
    <div class="content-v-layout">
      <navigation-bar
        class="navigation-bar"
        :type="navigationStyle"
        :title="$t('couple_sing_signup')"
        @on-back-click="onNavBackClick($event)"
      />
      <span class="title-text-view">{{ $t("couple_sing_activity_name") }}</span>
      <span class="please-fill-in-text-view">{{
        $t("couple_sing_please_fill")
      }}</span>
      <signup-fill-section
        :title="$t('couple_sing_song_name')"
        class="margin-top-81"
      >
        <input
          v-model="songName"
          class="fill-edit-view"
          :placeholder="$t('common_fill_out')"
        />
      </signup-fill-section>
      <signup-fill-section
        :title="$t('couple_sing_song_singer')"
        class="margin-top-81"
      >
        <input
          v-model="songSinger"
          class="fill-edit-view"
          :placeholder="$t('common_fill_out')"
        />
      </signup-fill-section>
      <signup-fill-section
        :title="$t('couple_sing_whats_app')"
        class="margin-top-81"
      >
        <input
          v-model="whatsApp"
          class="fill-edit-view"
          :placeholder="$t('common_fill_out')"
        />
      </signup-fill-section>
      <span
        :class="[signupBtnEnable ? 'signup-button' : 'signup-disable-button']"
        @click="onSignupBtnClick"
        >{{ $t("couple_confirm_signup") }}</span
      >
      <span class="confirm-tip-text-view">{{ $t("couple_confirm_tip") }}</span>
    </div>
    <van-popup
      v-model="showSignupSuccessTipDialog"
      get-container="body"
      round
      :close-on-click-overlay="false"
    >
      <common-dialog
        :title="$t('couple_audit_title')"
        :messageText="$t('couple_signup_success_tip')"
        :primaryButtonText="$t('common_confirm')"
        @on-primary-button-click="onSignupSuccessDialogConfirmClick"
      />
    </van-popup>
  </div>
</template>
<script>
import _ from "lodash";
import Vue from "vue";
import { Image as VanImage, Toast, Popup } from "vant";
import {
  hideNavigationBar,
  closeWindow,
  getMyUserInfo,
} from "../../../common/bridge";
import { NAVIGATION_BAR_STYLE } from "@/common/constant/index";
import NavigationBar from "@/common/components/NavigationBar";
import api from "../api/index";
import { RES_COMMON_CODE } from "../../../common/network/constant";
import CommonDialog from "@/common/components/CommonDialog";
import SignupFillSection from "./components/SignupFillSection.vue";

Vue.use(VanImage);
Vue.use(Toast);
Vue.use(Popup);
export default {
  name: "CoupleSingSignupFillIn",
  components: {
    "navigation-bar": NavigationBar,
    "signup-fill-section": SignupFillSection,
    "common-dialog": CommonDialog,
  },
  created() {
    hideNavigationBar();
    this.activityId = this.$route.query.activity_id;
  },
  data() {
    return {
      navigationStyle: NAVIGATION_BAR_STYLE.LIGHT,
      activityId: null,
      songName: "",
      songSinger: "",
      whatsApp: "",
      showSignupSuccessTipDialog: false,
    };
  },
  computed: {
    signupBtnEnable() {
      return (
        !_.isEmpty(this.songName) &&
        !_.isEmpty(this.songSinger) &&
        !_.isEmpty(this.whatsApp)
      );
    },
  },
  methods: {
    onNavBackClick() {
      window.history.length > 1 ? this.$router.go(-1) : closeWindow();
    },
    async onSignupBtnClick() {
      const signupParam = {
        songName: this.songName,
        singer: this.songSinger,
        whatsApp: this.whatsApp,
      };
      const { code } = await api.signUp({
        activityId: this.activityId,
        signupParam: JSON.stringify(signupParam),
      });
      if (code === RES_COMMON_CODE.SUCCESS) {
        this.showSignupSuccessTipDialog = true;
      } else if (code === RES_COMMON_CODE.USER_NO_CPINTIMACY) {
        Toast(this.$t("couple_sing_no_cp_tip"));
      } else if (code === RES_COMMON_CODE.ACTIVITY_SIGNUP_OVER) {
        Toast(this.$t("couple_signup_time_over"));
      } else if (code === RES_COMMON_CODE.RESCODE_OPERATION_LIMIT) {
        Toast(this.$t("couple_signup_num_over"));
      } else {
        Toast(this.$t("common_failed_with_errorcode", { code }));
      }
    },
    onSignupSuccessDialogConfirmClick() {
      this.showSignupSuccessTipDialog = false;
      window.history.length > 1 ? this.$router.go(-1) : closeWindow();
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";
@include we(page) {
  width: 750px;
  box-sizing: border-box;
  position: relative;
  .content-bg {
    width: 100%;
    height: 100%;
    background-image: url("./assets/couple_signup_fillin_bg.png");
    background-size: 100% 100%;
    position: fixed;
    z-index: -1;
  }
  @include v-layout(content) {
    width: 100%;
    height: 100%;
    padding-bottom: 100px;
    .navigation-bar {
      position: fixed;
    }
    @include text-view(
      title,
      $font-size: 66px,
      $line-height: 70px,
      $font-color: $--color-white,
      $font-weight: $--font-weight-bold,
      $text-align: start,
      $line-number: 1
    ) {
      width: 100%;
      margin-top: 200px;
      @include compat.padding-start(60px);
    }
    @include text-view(
      please-fill-in,
      $font-size: 32px,
      $line-height: 34px,
      $font-color: #ffffffb3,
      $font-weight: $--font-weight-regular,
      $text-align: start,
      $line-number: 1
    ) {
      width: 100%;
      margin-top: 33px;
      @include compat.padding-start(60px);
    }
    .margin-top-81 {
      margin-top: 81px;
    }
    @include edit-view(
      fill,
      $width: 100%,
      $height: 96px,
      $font-size: 28px,
      $font-color: $--color-white,
      $font-weight: $--font-weight-regular,
      $background-color: $--color-transparent
    ) {
      padding: 19px 0px;
      margin-top: 16px;
    }
    input::placeholder {
      color: white;
    }
    @include button(
      signup,
      $width: 362px,
      $height: 96px,
      $text-size: 30px,
      $text-color: #897bf5,
      $border-radius: 48px,
      $background: $--color-white
    ) {
      margin-top: 80px;
    }
    .signup-disable-button {
      @extend .signup-button;
      opacity: 0.5;
      pointer-events: none;
    }
    @include text-view(
      confirm-tip,
      $font-size: 24px,
      $line-height: 30px,
      $font-color: $--color-white,
      $font-weight: $--font-weight-regular,
      $text-align: center,
    ) {
      width: 600px;
      margin-top: 32px;
    }
  }
}
</style>
