<template>
  <div class="record-item-h-layout">
    <van-image class="prize-icon-image-view" :src="record.winGoods.icon" />
    <div class="record-detail-v-layout">
      <span class="prize-name-text-view">{{ record.winGoods.fullName }}</span>
      <span class="time-text-view">{{ time }}</span>
    </div>
  </div>
</template>
<script>
import moment from "moment";
import Vue from "vue";
import { Image as VanImage } from "vant";

Vue.use(VanImage);

export default {
  name: "RecordItemView",
  props: {
    record: {
      type: Object,
      default() {
        return {
          createTime: 0,
          winGoods: {
            icon: "",
            fullName: "",
          },
        };
      },
    },
  },
  computed: {
    time() {
      return moment(this.record.createTime).format("YYYY.MM.DD HH:mm:ss");
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include h-layout(record-item) {
  width: 686px;
  height: 160px;
  background: rgba(97, 5, 21, 0.6);
  border-radius: 20px;
  padding: 20px;

  @include image-view(prize-icon, 120px, 120px);

  @include v-layout(record-detail, flex-start) {
    @include compat.margin-start(24px);

    @include text-view(
      prize-name,
      $font-size: 30px,
      $line-height: 42px,
      $font-color: $--color-FFDD99,
      $font-weight: $--font-weight-medium,
      $font-family: $--font-family-PingFangSC,
      $line-number: 1
    );
    @include text-view(
      time,
      $font-size: 24px,
      $line-height: 29px,
      $font-color: rgba(255, 221, 153, 0.5),
      $font-weight: $--font-weight-regular,
      $line-number: 1
    ) {
      margin-top: 16px;
    }
  }
}
</style>