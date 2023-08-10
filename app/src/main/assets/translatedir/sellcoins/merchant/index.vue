<template>
  <page
    bgColor="#F5F7FA"
    contentBGColor="#F5F7FA"
    :navigation="{ title: $t('merchant_list'), endIcons: navigationEndIcons }"
    @on-end-icon-click="onEndIconClick($event)"
  >
    <van-empty
      v-if="isAreaMerchantListEmpty && merchantLoaded"
      class="area-merchant-list-empty-view"
      :image="require('@/common/assets/common_content_empty_ic.png')"
      :description="$t('merchant_area_list_empty')"
    />
    <div class="merchant-list-v-layout">
      <van-empty
        v-if="isRecommended"
        class="area-merchant-list-empty-view-half"
        :image="require('@/common/assets/common_content_empty_ic.png')"
        :description="$t('merchant_area_list_empty')"
      />
      <span class="recommend-merchant-text-view" v-if="isRecommended">{{
        $t("merchant_recommend_merchant")
      }}</span>
      <merchant-item
        class="merchant-item"
        v-for="(item, index) in merchantList"
        :key="index"
        :merchantInfo="item"
        :myUid="myUid"
      ></merchant-item>
    </div>
  </page>
</template>
<script>
import _ from "lodash";
import Vue from "vue";
import { Image as VanImage, Empty } from "vant";
import Page from "@/common/components/Page.vue";
import { hideNavigationBar, getMyUid } from "../../../common/bridge/index";
import MerchantItem from "./components/MerchantItem";
import api from "../api/index";
import { RES_COMMON_CODE } from "../../../common/network/constant";

const NAVIGATION_END_ICON_ACTION = {
  REPORT: 0,
};

Vue.use(VanImage);
Vue.use(Empty);
export default {
  name: "MerchantList",
  components: {
    page: Page,
    "merchant-item": MerchantItem,
  },
  data() {
    return {
      navigationEndIcons: [
        {
          action: NAVIGATION_END_ICON_ACTION.REPORT,
          icon: require("../../common/assets/common_report_ic.png"),
        },
      ],
      countryCode: "EG",
      isRecommended: false,
      merchantList: [],
      merchantLoaded: false,
      myUid: null,
    };
  },
  created() {
    hideNavigationBar();
    this.countryCode = this.$route.params.country_code;
    this.fetchMyUid();
    this.getMerchantList();
  },
  computed: {
    isAreaMerchantListEmpty() {
      return _.isEmpty(this.merchantList);
    },
  },
  methods: {
    async getMerchantList() {
      const { code, data } = await api.getMerchantList({
        countryCode: this.countryCode,
      });
      this.merchantLoaded = true;
      if (code === RES_COMMON_CODE.SUCCESS) {
        this.isRecommended = data.recommend === 1;
        this.merchantList = data.merchantInfoList;
      }
    },
    onEndIconClick(action) {
      if (action === NAVIGATION_END_ICON_ACTION.REPORT) {
        this.$router.push("/report_merchant");
      }
    },
    async fetchMyUid() {
      this.myUid = await getMyUid();
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/text.scss" as text;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";
.area-merchant-list-empty-view-half {
  width: 100%;
  height: 420px;
}
.area-merchant-list-empty-view {
  width: 100%;
  height: 900px;
}
@include v-layout(merchant-list) {
  padding-bottom: 55px;
  .merchant-item {
    margin-top: 16px;
  }
  @include text-view(
    recommend-merchant,
    $font-size: 28px,
    $line-height: 40px,
    $font-color: #25252f,
    $font-weight: $--font-weight-regular,
    $text-align: start
  ) {
    width: 100%;
  }
}
</style>
