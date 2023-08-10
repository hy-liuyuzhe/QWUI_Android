<template>
  <div class="publishment-record-item-h-layout" @click="onItemClick">
    <div class="start-h-layout">
      <van-image class="avatar-image-view" :src="record.avatar" round />
      <div class="detail-v-layout">
        <span class="name-text-view"> {{ record.name }} </span>
        <span class="punish-time-text-view">
          {{ formatPunishTime(record.ts) }}
        </span>
      </div>
    </div>

    <van-image
      class="go-arrow-image-view"
      :src="require('@/common/assets/common_arrow_end_gray_36_ic.png')"
    />

    <van-popup
      v-model="showPunishmentDetailPopup"
      position="bottom"
      round
      get-container="body"
    >
      <punishment-detail-view :record="record" />
    </van-popup>
  </div>
</template>
<script>
import Vue from "vue";
import { Image as VanImage } from "vant";
import moment from "moment";
import PunishmentDetailView from "@/superadmin/order-manage/components/PunishmentDetailView";

Vue.use(VanImage);

export default {
  name: "PublishmentRecordItemView",
  components: {
    "punishment-detail-view": PunishmentDetailView,
  },
  props: {
    record: {
      type: Object,
      default() {
        return {
          avatar: "",
          name: "",
          ts: 0,
        };
      },
    },
  },
  data() {
    return {
      showPunishmentDetailPopup: false,
    };
  },
  methods: {
    formatPunishTime(ts) {
      return moment(ts).format("MM.DD HH:mm");
    },
    onItemClick() {
      this.showPunishmentDetailPopup = true;
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/index.scss" as *;
@use "@/common/css/compat.scss" as compat;
@import "@/common/css/var.scss";

@include h-layout(publishment-record-item) {
  width: 100%;
  height: 140px;
  @include compat.padding-start(30px);
  @include compat.padding-end(30px);
  justify-content: space-between;

  &:active {
    opacity: 0.5;
  }

  @include h-layout(start) {
    @include image-view(avatar, 96px, 96px);

    @include v-layout(detail, flex-start) {
      @include compat.margin-start(30px);
      @include compat.margin-end(30px);

      @include text-view(
        name,
        $font-size: 32px,
        $line-height: 38px,
        $font-color: #222222,
        $font-weight: $--font-weight-bold,
        $font-family: $--font-family-Helvetica,
        $line-number: 1
      );

      @include text-view(
        punish-time,
        $font-size: 24px,
        $line-height: 29px,
        $font-color: #aaaaaa,
        $line-number: 1
      ) {
        margin-top: 8px;
      }
    }
  }

  @include image-view(go-arrow, 36px, 36px) {
    @include dir {
      transform: rotateY(180deg);
    }
  }
}
</style>