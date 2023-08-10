<template>
  <page
    bgColor="#F5F7FA"
    contentBGColor="#F5F7FA"
    :navigation="{ title: title }"
  >
    <div class="channel-recharge-v-layout">
      <title-layout :title="$t('trade_recharge_official_account')">
        <div class="offical-account-h-layout">
          <span class="account-text-view">{{ officialAccount }}</span>
          <span class="copy-button" @click="onOfficalAccountCopyClick">
            {{ $t("common_copy") }}
          </span>
        </div>
      </title-layout>
      <title-layout
        class="margin-top-16px"
        :title="$t('trade_recharge_my_payment_account')"
      >
        <input
          v-model="myAccount"
          class="my-account-edit-view"
          :placeholder="$t('common_fill_out')"
        />
      </title-layout>
      <title-layout
        class="margin-top-16px"
        :title="$t('trade_recharge_payment_amount')"
      >
        <div class="payment-amount-h-layout">
          <input
            v-model="paymentAmount"
            class="amount-edit-view"
            :placeholder="$t('common_fill_out')"
          />
          <span class="current-name-text-view">$</span>
        </div>
      </title-layout>
      <title-layout
        class="margin-top-16px"
        :title="$t('trade_recharge_payment_screenshot')"
      >
        <upload-file-add-view v-model="files" :maxCount="1" />
      </title-layout>
      <span class="confirm-button" @click="onConfirmClick">
        {{ $t("common_confirm") }}
      </span>
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
import Vue from "vue";
import { Toast, Image as VanImage, Overlay, Loading } from "vant";
import Page from "@/common/components/Page.vue";
import { RES_COMMON_CODE } from "../../../../common/network/constant";
import { hideNavigationBar } from "../../../../common/bridge/index";
import TitleLayout from "@/trade/recharge/channel-recharge/components/TitleLayout";
import UploadFileAddView from "@/common/components/UploadFileAddView";
import { uploadImgs } from "../../../../common/oss/index";
import { RECHARGE_CHANNEL } from "../constant/index";
import api from "../../api/index";
import VueClipboard from "vue-clipboard2";

Vue.use(Toast);
Vue.use(VanImage);
Vue.use(VueClipboard);
Vue.use(Overlay);
Vue.use(Loading);

export default {
  name: "TradeChannelRecharge",
  components: {
    page: Page,
    "title-layout": TitleLayout,
    "upload-file-add-view": UploadFileAddView,
  },
  created() {
    hideNavigationBar();
    this.channel = parseInt(this.$route.params.channel);
    this.myAccount = this.mySavedAccount;
  },
  data() {
    return {
      channel: 0,
      myAccount: "",
      paymentAmount: "",
      files: [],
      showLoading: false,
    };
  },
  methods: {
    onOfficalAccountCopyClick() {
      this.$copyText(this.officialAccount).then(
        (e) => {
          Toast(this.$t("common_copy_success"));
        },
        (e) => {
          Toast(this.$t("common_copy_failed"));
        }
      );
    },
    async onConfirmClick() {
      this.showLoading = true;
      const { code, data } = await uploadImgs(this.files);
      if (code !== RES_COMMON_CODE.SUCCESS) {
        Toast(this.$t("common_failed_with_errorcode", { code }));
        this.showLoading = false;
        return;
      }

      data.forEach((result) => {
        const { code, data } = result;
        if (code === RES_COMMON_CODE.SUCCESS) {
          this.recharge(data);
        } else {
          Toast(this.$t("common_upload_failed"));
          this.showLoading = false;
        }
      });
    },
    async recharge(url) {
      const { code } = await api.recharge({
        channel: this.channel,
        amount: this.paymentAmount,
        payAccount: this.myAccount,
        payImg: url,
      });
      this.showLoading = false;
      if (code === RES_COMMON_CODE.SUCCESS) {
        Toast(this.$t("common_success"));
        return;
      }

      if (code === RES_COMMON_CODE.NO_PERMISSION) {
        Toast(this.$t("common_no_permission"));
        return;
      }

      Toast(this.$t("common_failed_with_errorcode", { code }));
    },
  },
  computed: {
    officialAccount() {
      const { officialPayonner, officialPaypal } =
        this.$store.state.trade.rechargeAccountInfo;
      if (this.channel === RECHARGE_CHANNEL.PAYONNER) {
        return officialPayonner;
      }

      if (this.channel === RECHARGE_CHANNEL.PAYPAL) {
        return officialPaypal;
      }

      return "";
    },
    mySavedAccount() {
      const { myPayonner, myPaypal } =
        this.$store.state.trade.rechargeAccountInfo;
      if (this.channel === RECHARGE_CHANNEL.PAYONNER) {
        return myPayonner;
      }

      if (this.channel === RECHARGE_CHANNEL.PAYPAL) {
        return myPaypal;
      }

      return "";
    },
    title() {
      if (this.channel === RECHARGE_CHANNEL.PAYONNER) {
        return this.$t("trade_recharge_payonner_recharge");
      }

      if (this.channel === RECHARGE_CHANNEL.PAYPAL) {
        return this.$t("trade_recharge_paypal_recharge");
      }

      return "";
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include v-layout(channel-recharge) {
  width: 100%;

  .margin-top-16px {
    margin-top: 16px;
  }

  @include h-layout(offical-account) {
    width: 100%;
    height: 98px;
    border-radius: 32px;
    border: 2px solid #f3f3f3;
    justify-content: space-between;
    padding: 0px 24px;

    @include text-view(
      account,
      $font-size: 28px,
      $line-height: 34px,
      $font-color: $--color-777777,
      $font-weight: $--font-weight-regular
    );

    @include button(
      copy,
      $width: 144px,
      $height: 56px,
      $text-size: 28px,
      $text-color: $--color-white,
      $border-radius: 32px,
      $background: $--color-00E0D7
    );
  }

  @include edit-view(
    my-account,
    $width: 100%,
    $height: 98px,
    $font-size: 28px,
    $font-color: $--color-AAAAAA,
    $font-weight: $--font-weight-regular,
    $background-color: $--color-F5F7FA
  ) {
    border-radius: 32px;
    padding: 0px 24px;
  }

  @include h-layout(payment-amount) {
    width: 100%;
    height: 98px;
    background: $--color-F5F7FA;
    border-radius: 32px;
    justify-content: space-between;
    padding: 0px 24px;

    @include edit-view(
      amount,
      $width: 596px,
      $height: 98px,
      $font-size: 28px,
      $font-color: $--color-AAAAAA,
      $font-weight: $--font-weight-regular,
      $background-color: $--color-F5F7FA
    );
    @include text-view(
      current-name,
      $font-size: 36px,
      $line-height: 48px,
      $font-color: $--color-222222,
      $font-weight: $--font-weight-bold
    );
  }

  @include button(
    confirm,
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

  .loading-view {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100%;
  }
}
</style>