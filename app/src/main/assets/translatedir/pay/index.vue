<template>
  <div class="pay_container">
    <div class="recharge-title-container">
      <span class="recharge-title">{{ $t("recharge_by") }}</span>
      <div class="recharge-location" @click="onLocationClick">
        <van-image
          :src="require('./assets/pay_location_ic.png')"
          class="location-icon"
        ></van-image>
        <span class="location-text">{{ location }}</span>
      </div>
    </div>
    <van-list
      v-model="loading"
      :finished="finished"
      finished-text=""
      class="channel-list"
    >
      <pay-channel-collapse
        v-for="(item, index) in channels"
        :key="index"
        class="channel-item"
        v-bind:channelInfo="item"
        @on-go-merchant-list-click="onGoMerchantListClick"
      />
    </van-list>
  </div>
</template>
<script>
import PayChannelCollapse from "./components/PayChannelCollapse.vue";
import Vue from "vue";
import { List } from "vant";
import { Image as VanImage } from "vant";
import {
  addNativeMessageListener,
  removeNativeMessageListener,
} from "../../common/bridge/base";
import {
  dispatchDeeplink,
  getSelectedPayChannelCountry,
  openFullPageWebView,
} from "../../common/bridge/index";
import { MSG_TYPE } from "../../common/bridge/constant";
import { getCountryCode } from "../../common/locale";
import { Toast } from "vant";
import { isDevEnv } from "../../common/network/http/index";

Vue.use(List);
Vue.use(VanImage);
Vue.use(Toast);
export default {
  name: "pay",
  components: {
    "pay-channel-collapse": PayChannelCollapse,
  },
  beforeCreate() {
    document.querySelector("body").setAttribute("style", "background:#F5F7FA");
  },
  created() {
    var self = this;
    addNativeMessageListener(MSG_TYPE.COUNTRY_SELECT, function(message) {
      if (message) {
        const { type, data } = message;
        if (type && type === MSG_TYPE.COUNTRY_SELECT) {
          if (
            data &&
            data.selectedCountryCode &&
            data.selectedCountryCode.length > 0
          ) {
            self.countryCode = data.selectedCountryCode;
            self.refreshChannels();
          }
        }
      }
    });
    addNativeMessageListener(MSG_TYPE.REFRESH_PRODUCT, function(message) {
      if (message) {
        const { type } = message;
        if (type && type === MSG_TYPE.REFRESH_PRODUCT) {
          self.refreshProducts();
        }
      }
    });
    this.getSelectedCountryCode();
  },
  destroyed() {
    removeNativeMessageListener(MSG_TYPE.COUNTRY_SELECT);
    removeNativeMessageListener(MSG_TYPE.REFRESH_PRODUCT);
  },
  data() {
    return {
      loading: false,
      finished: true,
      countryCode: "",
    };
  },
  metaInfo: {
    meta: [
      {
        name: "viewport",
        content: "width=device-width, initial-scale=1, maximum-scale=1",
      },
    ],
  },
  computed: {
    channels() {
      return this.$store.state.pay.channels;
    },
    location() {
      if (this.countryCode.length == 0) {
        return "";
      } else {
        return this.$t(`${this.countryCode}`);
      }
    },
  },
  methods: {
    refreshChannels() {
      this.$store.dispatch("pay/requestChannelList", {
        countryCode: this.countryCode,
      });
    },
    refreshProducts() {
      this.refreshChannels();
    },
    onLocationClick() {
      dispatchDeeplink({
        deeplink: "wenext://wayak/countrySelect",
        path: "/countrySelect",
      });
    },
    async getSelectedCountryCode() {
      const result = await getSelectedPayChannelCountry();
      if (
        result &&
        result.selectedPayChannelCountry &&
        result.selectedPayChannelCountry.length > 0
      ) {
        this.countryCode = result.selectedPayChannelCountry;
        this.refreshChannels();
        return;
      }
      const countryCode = await getCountryCode();
      if (countryCode && countryCode.length > 0) {
        this.countryCode = countryCode;
        this.refreshChannels();
        return;
      }
      this.countryCode = "";
      this.refreshChannels();
    },
    onGoMerchantListClick() {
      let url = `https://wayak.wenext.chat/merchant_list/${this.countryCode}`;
      if (isDevEnv()) {
        url = `http://43.134.222.242:8080/merchant_list/${this.countryCode}`;
      }
      openFullPageWebView({
        url: url,
      });
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
.pay_container {
  width: 750px;
  height: 100%;
  text-align: start;
  background-color: #f5f7fa;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.recharge-title-container {
  width: 750px;
  height: 64px;
  display: flex;
  flex-direction: row;
}
.recharge-title {
  font-size: 28px;
  font-family: SFUIText-Regular, SFUIText;
  font-weight: 400;
  color: #222222;
  height: 40px;
  @include compat.margin-start(24px);
  margin-top: 24px;
}
.recharge-location {
  display: flex;
  flex-direction: row;
  align-items: center;
  height: 36px;
  margin-top: 26px;
  @include compat.margin-start(auto);
  @include compat.margin-end(24px);
}
.location-icon {
  width: 36px;
  height: 36px;
}
.location-text {
  font-size: 28px;
  font-family: SFUIText-Regular, SFUIText;
  font-weight: 400;
  color: #777777;
  @include compat.margin-start(4px);
}
.channel-list {
  top: 64px;
  width: 702px;
  padding-bottom: 80px;
}
.channel-item {
  width: 702px;
  margin-top: 16px;
  display: flex;
  flex-direction: column;
}
</style>