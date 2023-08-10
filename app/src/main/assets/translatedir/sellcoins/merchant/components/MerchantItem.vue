<template>
  <div class="merchant-v-layout">
    <div class="remain-coins-layout">
      <span class="remain-coins-title-text-view">{{
        $t("merchant_remain_coins")
      }}</span>
      <van-image
        class="coins-image-view"
        :src="require('../../../common/assets/common_coin_48_ic.png')"
      />
      <span
        :class="[
          merchantInfo.displayMode === 1
            ? 'blur-remain-coins-text-view'
            : 'remain-coins-text-view',
        ]"
        >{{ remainCoins }}</span
      >
    </div>
    <div class="divider-line"></div>
    <div class="detail-info-h-layout">
      <van-image
        class="avatar-image-view"
        :src="merchantInfo.avatar"
        round
        error-icon="user-circle-o"
      />
      <div class="detail-info-v-layout">
        <div class="name-h-layout">
          <van-image class="nation-flag-image-view" :src="nationFlag" />
          <span class="name-text-view">{{ merchantInfo.name }}</span>
        </div>
        <span class="id-text-view">ID:{{ merchantId }}</span>
        <span class="id-text-view"
          >Whatsapp number:{{ merchantInfo.whatsapp }}</span
        >
        <span
          class="private-letter-button"
          @click="onPrivateLetterClick"
          v-if="!isSelf"
          >{{ $t("common_private_letter") }}</span
        >
      </div>
      <div
        :class="[
          merchantInfo.online === 1
            ? 'online-status-h-layout'
            : 'offline-status-h-layout',
        ]"
      >
        <van-image class="online-image-view" :src="onlineIcon" />
        <span
          :class="[
            merchantInfo.online === 1
              ? 'online-text-view'
              : 'offline-text-view',
          ]"
          >{{ onlineDesc }}</span
        >
      </div>
    </div>
  </div>
</template>
<script>
import _ from "lodash";
import Vue from "vue";
import { Image as VanImage } from "vant";
import { getCountryNationFlag } from "../../../common/constant/countrySelect";
import { dispatchDeeplink } from "../../../../common/bridge/index";

Vue.use(VanImage);
export default {
  name: "MerchantItem",
  props: {
    merchantInfo: Object,
    myUid: Number,
  },
  computed: {
    remainCoins() {
      if (this.merchantInfo.coins < 100000) {
        return this.merchantInfo.coins;
      } else if (this.merchantInfo.coins < 1000000) {
        return "100000+";
      } else {
        return "1000000+";
      }
    },
    nationFlag() {
      return getCountryNationFlag(this.merchantInfo.country);
    },
    onlineDesc() {
      return this.merchantInfo.online === 1
        ? this.$t("common_online")
        : this.$t("common_offline");
    },
    onlineIcon() {
      return this.merchantInfo.online === 1
        ? require("../../../common/assets/common_online_ic.png")
        : require("../../../common/assets/common_offline_ic.png");
    },
    isSelf() {
      return this.myUid === this.merchantInfo.uid;
    },
    merchantId() {
      if (_.isNil(this.merchantInfo.goodId)) {
        return this.merchantInfo.sid;
      } else {
        return this.merchantInfo.goodId;
      }
    },
  },
  methods: {
    onPrivateLetterClick() {
      if (_.isNil(this.merchantInfo.uid)) {
        return;
      }
      dispatchDeeplink({
        deeplink: `wenext://wayak/session_list?extra_to_uid=${this.merchantInfo.uid}&extra_from_scene=15`,
        path: "/session_list",
      });
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/text.scss" as text;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";
@include v-layout(merchant) {
  width: 702px;
  box-sizing: border-box;
  padding: 24px 32px;
  background: #ffffff;
  border-radius: 8px;
  .remain-coins-layout {
    width: 100%;
    position: relative;
    @include text-view(
      remain-coins-title,
      $font-size: 26px,
      $line-height: 40px,
      $font-color: $--color-AAAAAA,
      $font-weight: $--font-weight-normal
    ) {
      position: absolute;
      top: 0;
      left: 0;
    }
    @include text-view(
      remain-coins,
      $font-size: 34px,
      $line-height: 40px,
      $font-color: $--color-222222,
      $font-weight: $--font-weight-bold
    ) {
      position: absolute;
      right: 48px;
      top: 0;
    }
    .blur-remain-coins-text-view {
      @extend .remain-coins-text-view;
      filter: blur(8px);
    }
    @include image-view(coins, 40px, 40px) {
      position: absolute;
      right: 0;
      top: 0;
    }
  }
  .divider-line {
    width: 638px;
    height: 1px;
    background: #e9e9ea;
    margin-top: 64px;
  }
  @include h-layout(detail-info) {
    width: 100%;
    margin-top: 24px;
    align-items: flex-start;
    position: relative;
    @include image-view(avatar, 96px, 96px);
    @include v-layout(detail-info, flex-start) {
      @include compat.margin-start(16px);
      @include h-layout(name) {
        margin-top: 6px;
        @include image-view(nation-flag, 40px, 27px);
        @include text-view(
          name,
          $font-size: 32px,
          $line-height: 40px,
          $font-color: #25252f,
          $font-weight: $--font-weight-regular,
          $line-number: 1
        ) {
          @include compat.margin-start(6px);
          width: 270px;
        }
      }
      @include text-view(
        id,
        $font-size: 26px,
        $line-height: 40px,
        $font-color: $--color-AAAAAA,
        $font-weight: $--font-weight-regular,
      ) {
        margin-top: 8px;
        width: 500px;
      }
      @include button(
        private-letter,
        $width: 260px,
        $height: 64px,
        $text-size: 28px,
        $text-color: $--color-white,
        $border-radius: 48px,
        $background: $--color-00E0D7
      ) {
        margin-top: 24px;
        @include compat.margin-start(77px);
      }
    }
    @include h-layout(online-status) {
      height: 32px;
      border-radius: 16px;
      border: 1px solid #00e0d7;
      position: absolute;
      top: 4px;
      right: 0px;
      @include compat.padding-end(13px);
      @include dir {
        left: 0px;
        right: unset;
      }
    }
    @include text-view(
      online,
      $font-size: 20px,
      $line-height: 32px,
      $font-color: $--color-00E0D7,
      $font-weight: $--font-weight-medium
    );
    @include image-view(online, 28px, 28px);
    @include h-layout(offline-status) {
      @extend .online-status-h-layout;
      border: 2px solid #efefef;
      .offline-text-view {
        @extend .online-text-view;
        color: #999999;
      }
    }
  }
}
</style>
