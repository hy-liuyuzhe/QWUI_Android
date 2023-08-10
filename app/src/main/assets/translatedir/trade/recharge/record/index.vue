<template>
  <page
    bgColor="#F5F7FA"
    contentBGColor="#F5F7FA"
    :navigation="{ title: $t('trade_recharge_record') }"
  >
    <van-list
      class="record-v-layout"
      v-model="loading"
      :finished="finished"
      :finished-text="$t('no_more')"
      :loading-text="$t('loading')"
      @load="loadMoreRecords"
    >
      <record-item-view
        v-for="(record, index) in records"
        class="margin-top-16px"
        :key="index"
        :record="record"
      />
    </van-list>
  </page>
</template>
<script>
import _ from "lodash";
import Vue from "vue";
import { Image as VanImage, Toast, List } from "vant";
import Page from "@/common/components/Page.vue";
import RecordItemView from "@/trade/recharge/record/components/RecordItemView";
import { hideNavigationBar } from "../../../../common/bridge/index";
import { RES_COMMON_CODE } from "../../../../common/network/constant";
import api from "../../api/index";

Vue.use(VanImage);
Vue.use(Toast);
Vue.use(List);

const PAGE_SIZE = 20;

export default {
  name: "AnchorWithdrawHistory",
  components: {
    page: Page,
    "record-item-view": RecordItemView,
  },
  data() {
    return {
      currentPage: 1,
      loading: true,
      finished: false,
      records: [],
    };
  },
  created() {
    hideNavigationBar();
    this.refreshRecords();
  },
  methods: {
    async refreshRecords() {
      const { code, data } = await api.getRechargeRecords(1);
      if (code !== RES_COMMON_CODE.SUCCESS) {
        Toast(this.$t("common_failed_with_errorcode", { code }));
        this.loading = false;
        return;
      }

      const { records } = data;
      if (records.length < PAGE_SIZE) {
        this.finished = true;
      }
      this.records = records;
      this.currentPage = 1;
      this.loading = false;
    },
    async loadMoreRecords() {
      const { code, data } = await api.getRechargeRecords(this.currentPage + 1);
      if (code !== RES_COMMON_CODE.SUCCESS) {
        Toast(this.$t("common_failed_with_errorcode", { code }));
        this.loading = false;
        return;
      }

      const { records } = data;
      if (records.length < PAGE_SIZE) {
        this.finished = true;
      }
      this.records = _.concat(this.records, records);
      this.currentPage = this.currentPage + 1;
      this.loading = false;
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include v-layout(record) {
  width: 100%;

  .margin-top-16px {
    margin-top: 16px;
  }
}
</style>