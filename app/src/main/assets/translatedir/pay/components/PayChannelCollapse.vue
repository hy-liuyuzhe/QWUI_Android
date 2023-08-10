<template>
  <div class="channel-root">
    <div
      :class="[
        this.expanding
          ? 'channel-title-container-expand'
          : 'channel-title-container-collapse',
      ]"
      @click="onHeaderClick"
    >
      <span class="more-cheat-lable" v-show="showMoreCheapLabel">{{
        $t("pay_channel_more_cheat")
      }}</span>
      <van-image class="channel-icon" :src="channelInfo.channelIcon" />
      <span class="channel-title">{{ channelName }}</span>
      <van-image class="arrow-icon" :src="arrowImageSrc" />
    </div>
    <div class="channel-product-list" v-show="showProductList">
      <coin-product-list
        :type="channelInfo.type"
        :canCollapse="canCollapse"
        :products="channelInfo.products"
      />
      <div class="bottom-container" @click="onHeaderClick">
        <van-image
          class="bottom-collapse-icon"
          :src="require('../assets/collapse_up.png')"
        />
      </div>
    </div>
  </div>
</template>
<script>
import Vue from "vue";
import { Image as VanImage } from "vant";
import CoinProductList from "./CoinProductGridList.vue";
import { CHANNEL } from "../constant/constant";
import { switchGoogleChannel } from "../../../common/bridge/index";

Vue.use(VanImage);
export default {
  name: "PayChannelCollapse",
  props: {
    channelInfo: Object,
  },
  data() {
    return {
      expanding: false,
    };
  },
  computed: {
    arrowImageSrc() {
      if (this.canCollapse) {
        return this.expanding
          ? require("../assets/collapse_up.png")
          : require("../assets/collapse_down.png");
      } else {
        return this.$i18n.locale == "ar"
          ? require("../assets/arrow_right_rtl.png")
          : require("../assets/arrow_right.png");
      }
    },
    showProductList() {
      if (this.canCollapse) {
        return this.expanding;
      } else {
        return false;
      }
    },
    canCollapse() {
      if (
        this.channelInfo.type === CHANNEL.CHANNEL_GOOGLE ||
        this.channelInfo.type === CHANNEL.CHANNEL_MERCHANT ||
        this.channelInfo.type == CHANNEL.CHANNEL_HUAWEI
      ) {
        return false;
      } else {
        return true;
      }
    },
    channelName() {
      if (this.channelInfo.type === CHANNEL.CHANNEL_MERCHANT) {
        return this.$t("pay_channel_merchant_ship");
      } else {
        return this.channelInfo.name;
      }
    },
    showMoreCheapLabel() {
      if (this.channelInfo.type === CHANNEL.CHANNEL_MERCHANT) {
        return true;
      } else {
        return false;
      }
    },
  },
  methods: {
    onHeaderClick() {
      if (this.canCollapse) {
        this.expanding = !this.expanding;
        if (this.expanding) {
          this.$store.dispatch("pay/requestProductListIfNeed", {
            countryCode: this.channelInfo.countryCode,
            type: this.channelInfo.type,
          });
        }
      } else if (this.channelInfo.type === CHANNEL.CHANNEL_GOOGLE || this.channelInfo.type === CHANNEL.CHANNEL_HUAWEI) {
        switchGoogleChannel();
      } else if (this.channelInfo.type === CHANNEL.CHANNEL_MERCHANT) {
        this.goMerchantList();
      } else {
        //ntd.
      }
    },
    goMerchantList() {
      this.$emit("on-go-merchant-list-click");
    },
  },
  components: {
    "coin-product-list": CoinProductList,
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/index.scss" as *;
@use "@/common/css/compat.scss" as compat;

.channel-root {
  width: 702px;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.channel-title-container {
  width: 702px;
  height: 128px;
  border: 1px solid #e9e9e9;
  background-color: #ffffff;
  display: flex;
  flex-direction: row;
  align-items: center;
  position: relative;
}
.channel-title-container-expand {
  @extend .channel-title-container;
  border-radius: 24px 24px 0px 0px;
}
.channel-title-container-collapse {
  @extend .channel-title-container;
  border-radius: 24px;
}
.channel-icon {
  width: 160px;
  height: 96px;
  @include compat.margin-start(24px);
}
.channel-title {
  font-size: 32px;
  font-family: SFUIText-Regular, SFUIText;
  font-weight: 400;
  color: #25252f;
  line-height: 40px;
  @include compat.margin-start(16px);
}
.arrow-icon {
  width: 32px;
  height: 32px;
  @include compat.margin-start(auto);
  @include compat.margin-end(24px);
}
.channel-product-list {
  width: 702px;
  border-radius: 0px 0px 24px 24px;
  border: 1px solid #e9e9e9;
  border-width: 0px 1px 1px;
  background-color: #ffffff;
  padding-top: 16px;
}
.bottom-container {
  height: 88px;
  text-align: center;
}
.bottom-collapse-icon {
  width: 32px;
  height: 32px;
  margin-top: 24px;
}
.more-cheat-lable {
  padding: 3px 20px;
  background: #ff196b;
  border-radius: 0px 24px 0px 16px;
  font-size: 20px;
  font-family: SFUIText-Regular, SFUIText;
  font-weight: 400;
  color: #ffffff;
  line-height: 26px;
  position: absolute;
  top: 0;
  right: 0;
  text-align: center;
  @include dir {
    left: 0;
    right: unset;
    border-radius: 24px 0px 16px 0px;
  }
}
</style>
