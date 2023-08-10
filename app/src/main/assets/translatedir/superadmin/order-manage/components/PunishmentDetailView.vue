<template>
  <div class="punishment-detail-v-layout">
    <span class="punishment-detail-title-text-view">
      {{ $t("super_admin_punishment_detail") }}
    </span>
    <div class="user-info-h-layout" @click="onUserInfoClick">
      <van-image class="avatar-image-view" :src="record.avatar" round />
      <div class="detail-v-layout">
        <span class="name-text-view">{{ record.name }}</span>
        <span class="id-text-view">{{ record.sid }}</span>
      </div>
    </div>
    <div class="punish-top-divide-line" />
    <div class="punishment-v-layout">
      <span class="title-text-view">
        {{ $t("super_admin_punishment_operation") }}
      </span>
      <span class="content-text-view">{{ getPuishmentOperationsText() }}</span>
    </div>
    <div class="punishment-v-layout punishment-reason-layout">
      <span class="title-text-view">
        {{ $t("super_admin_punishment_reason") }}
      </span>
      <span class="content-text-view">{{ getPuishmentReasonText() }}</span>
    </div>
  </div>
</template>
<script>
import _ from "lodash";
import Vue from "vue";
import { Image as VanImage } from "vant";
import {
  PUISHMENT_OPERATION_LOCALE_KEY,
  PUISHMENT_REASON_LOCALE_KEY,
} from "../../constant/index";
import { goUserProfle } from "../../../../common/bridge/index";

Vue.use(VanImage);

export default {
  name: "PunishmentDetailView",
  props: {
    record: {
      type: Object,
      default() {
        return {
          uid: 0,
          sid: 0,
          avatar: "",
          name: "",
          operations: [],
          reason: 0,
        };
      },
    },
  },
  methods: {
    getPunishmentOperationText(operation) {
      const key = PUISHMENT_OPERATION_LOCALE_KEY[operation];
      if (_.isNil(key)) {
        return "";
      }

      return this.$t(key);
    },
    getPunishmentReasonText(reason) {
      const key = PUISHMENT_REASON_LOCALE_KEY[reason];
      if (_.isNil(key)) {
        return this.$t("super_admin_punishment_reason_other");
      }

      return this.$t(key);
    },
    getPuishmentOperationsText() {
      let text = "";
      this.record.operations.forEach((operation, index) => {
        text += `${index + 1}. ${this.getPunishmentOperationText(operation)}\n`;
      });
      return text;
    },
    getPuishmentReasonText() {
      return this.getPunishmentReasonText(this.record.reason);
    },
    onUserInfoClick() {
      goUserProfle(this.record.uid);
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/index.scss" as *;
@use "@/common/css/compat.scss" as compat;
@import "@/common/css/var.scss";

@include v-layout(punishment-detail, flex-start) {
  width: 750px;
  padding: 30px;
  background: #ffffff;

  @include text-view(
    punishment-detail-title,
    $font-size: 30px,
    $line-height: 36px,
    $font-color: #222222,
    $font-weight: $--font-weight-bold,
    $text-align: center,
    $line-number: 1
  ) {
    align-self: center;
  }

  @include h-layout(user-info) {
    width: 100%;
    height: 160px;
    margin-top: 30px;

    @include image-view(avatar, 96px, 96px);

    @include v-layout(detail) {
      @include compat.margin-start(24px);

      @include text-view(
        name,
        $font-size: 32px,
        $line-height: 38px,
        $font-color: #222222,
        $font-family: $--font-family-Helvetica,
        $line-number: 1
      );

      @include text-view(
        id,
        $font-size: 24px,
        $line-height: 29px,
        $font-color: #aaaaaa,
        $font-family: $--font-family-Helvetica,
        $line-number: 1
      ) {
        margin-top: 8px;
      }
    }
  }

  @include divide-line(punish-top, 686px, 2px, #f3f3f3);

  @include v-layout(punishment, flex-start) {
    
    @include text-view(
      title,
      $font-size: 28px,
      $line-height: 80px,
      $font-color: #222222,
      $font-weight: $--font-weight-bold,
      $line-number: 1
    );
    @include text-view(
      content,
      $font-size: 28px,
      $line-height: 34px,
      $font-color: #777777,
      $line-number: 0
    ) {
      margin-top: 8px;
    }
  }

  .punishment-reason-layout {
    margin-top: 30px;
    margin-bottom: 50px;
  }
}
</style>