<template>
  <div>
    <van-grid v-model="loading" :column-num="2" :gutter="16" :border="false">
      <!-- <van-grid-item v-for="value in 6" :key="value" icon="photo-o" text="文字" /> -->
      <van-grid-item v-for="(item, index) in products" :key="index">
        <coin-product-item v-bind:productInfo="item" @recharge-btn-click="onRechargeClick($event, productId)" />
      </van-grid-item>
    </van-grid>
  </div>
</template>
<script>
import Vue from "vue";
import { Grid, GridItem } from 'vant';
import { Toast } from "vant";
import CoinPruductItem from "./CoinProductGridItem.vue";
import api from "../api/index";
import { RES_COMMON_CODE } from "../../../common/network/constant";
import {
  openFullPageWebView,
  reportAfBackEvent,
} from "../../../common/bridge/index";
import { ORDER_PRODUCT_TYPE } from "../constant/constant";

Vue.use(Toast);
Vue.use(Grid);
Vue.use(GridItem);

export default {
  data() {
    return {
      loading: false,
      finished: true,
    };
  },
  props: {
    type: Number,
    canCollapse: Boolean,
    products: Array,
  },
  created() {
    console.log("products length is " + this.products.length)
  },
  name: "CoinProductGridList",
  components: {
    "coin-product-item": CoinPruductItem,
  },
  methods: {
    async onRechargeClick(productId) {
      Toast.loading({
        message: "",
        forbidClick: true,
        loadingType: "spinner",
      });
      reportAfBackEvent({
        event_name: "recharge_c000",
      });
      const res = await api.createOrder({
        payPackageId: productId,
        type: this.type,
        orderProductType: ORDER_PRODUCT_TYPE.TYPE_COIN,
      });
      const { code, data } = res;
      if (code == RES_COMMON_CODE.SUCCESS) {
        openFullPageWebView({
          url: data.requestUrl,
        });
        Toast.clear();
      } else {
        Toast("Fail");
      }
    },
  },
};
</script>
<style lang="scss" scoped>
.product-item {
  width: 678px;
  height: 120px;
  background-color: #f2f8fb;
  border-radius: 16px;
  margin-left: 12px;
  margin-right: 12px;
  margin-top: 12px;
  display: flex;
  flex-direction: row;
  align-items: center;
}
</style>
