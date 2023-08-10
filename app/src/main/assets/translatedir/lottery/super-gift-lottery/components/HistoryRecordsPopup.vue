<template>
  <div class="history-records-v-layout">
    <div class="bottom-v-layout">
      <van-image
        class="top-bg-image-view"
        :src="
          require('../assets/super_gift_lottery_history_records_top_bg.png')
        "
      />
      <van-empty
        v-if="empty"
        class="empty-view"
        :image="require('../../../common/assets/common_no_records_ic.png')"
        :description="$t('common_no_records')"
      />
      <div v-else class="records-list-v-layout">
        <record-item-view
          v-for="(record, index) in records"
          :key="index"
          :record="record"
          class="record-item"
        />
      </div>
    </div>
    <span class="title-text-view">
      {{ $t("super_gift_lottery_history_records") }}
    </span>
  </div>
</template>
<script>
import Vue from "vue";
import { Image as VanImage, Empty, Toast } from "vant";
import api from "../../api/index";
import { ACTIVITY_TYPE } from "../../constant/index";
import { RES_COMMON_CODE } from "../../../../common/network/constant";
import RecordItemView from "@/lottery/super-gift-lottery/components/RecordItemView";

Vue.use(VanImage);
Vue.use(Empty);
Vue.use(Toast);

export default {
  name: "HistoryRecordsPopup",
  components: {
    "record-item-view": RecordItemView,
  },
  created() {
    this.getLotteryResults();
  },
  data() {
    return {
      empty: false,
      records: [],
    };
  },
  methods: {
    async getLotteryResults() {
      const { code, data } = await api.getUserLotteryResults(
        ACTIVITY_TYPE.SUPER_GIFT_LOTTERY
      );
      if (code === RES_COMMON_CODE.SUCCESS) {
        this.empty = data.length === 0;
        this.records = data;
        return;
      }

      Toast(this.$t("common_failed_with_errorcode", { code }));
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include v-layout(history-records) {
  width: 100%;
  height: 1037px;
  @include text-view(
    title,
    $font-size: 38px,
    $line-height: 130px,
    $font-color: $--color-FFFDE6,
    $font-weight: $--font-weight-bold,
    $text-align: center,
    $line-number: 1
  ) {
    width: 427px;
    position: absolute;
    top: 0;
    text-shadow: 0px 0px 9px #ed3818;
    background-repeat: no-repeat;
    background-position: center;
    background-size: 100% 100%;
    background-image: url("../assets/super_gift_lottery_history_records_title_bg.png");
  }

  @include v-layout(bottom) {
    position: relative;
    top: 57px;
    background: linear-gradient(180deg, #9a163a 0%, #712216 100%);
    border-radius: 32px 32px 2px 2px;
    width: 100%;
    height: 980px;

    @include image-view(top-bg, 750px, 300px) {
      position: absolute;
      top: 0;
    }

    .empty-view {
      width: 100%;
      height: 100%;
    }

    @include v-layout(records-list) {
      display: block;
      width: 100%;
      height: 890px;
      margin-top: 90px;
      overflow-y: scroll;

      .record-item {
        margin-bottom: 24px;
        @include compat.margin-start(32px);
      }
    }
  }
}
</style>