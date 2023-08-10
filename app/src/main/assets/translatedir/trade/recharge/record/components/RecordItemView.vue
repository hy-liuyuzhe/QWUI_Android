<template>
  <div class="record-item-v-layout">
    <div class="title-h-layout">
      <span class="recharge-way-text-view">{{ channelText }}</span>
      <span class="recharge-amount-text-view">{{ amountText }}</span>
    </div>
    <div class="h-divide-line" />
    <coins-item-view
      class="margin-top-24px"
      :title="rechargeText"
      :coins="record.coins"
    />
    <coins-item-view
      v-if="isSuccess"
      :title="$t('trade_recharge_balance_before_recharge')"
      :coins="record.lastBalance"
    />
    <coins-item-view
      v-if="isSuccess"
      :title="$t('trade_recharge_current_balance')"
      :coins="record.newBalance"
    />
    <div :class="['h-divide-line', 'margin-top-24px']" />
    <div class="date-status-h-layout">
      <span class="date-text-view">
        {{ timeText }}
      </span>
      <span class="status-text-view" :style="statusStyle">
        {{ statusText }}
      </span>
    </div>
  </div>
</template>
<script>
import moment from "moment";
import { RECHARGE_CHANNEL, RECHARGE_STATUS } from "../../constant/index";
import CoinsItemView from "@/trade/recharge/record/components/CoinsItemView";

export default {
  name: "RecordItemView",
  components: {
    "coins-item-view": CoinsItemView,
  },
  props: {
    record: {
      type: Object,
      default() {
        return {
          channel: 0,
          amount: 0,
          coins: 0,
          newBalance: 0,
          lastBalance: 0,
          status: 0,
          createTime: 0,
        };
      },
    },
  },
  computed: {
    amountText() {
      return `+${this.record.amount}$`;
    },
    isThirdPartyCompensation() {
      const channel = this.record.channel;
      return channel === RECHARGE_CHANNEL.FAWRY;
    },
    channelText() {
      const channel = this.record.channel;
      if (channel === RECHARGE_CHANNEL.SALARY_ADVANCE) {
        return this.$t("trade_recharge_channel_salary_advance");
      }

      if (channel === RECHARGE_CHANNEL.PAYONNER) {
        return this.$t("trade_recharge_channel_payonner");
      }

      if (channel === RECHARGE_CHANNEL.PAYPAL) {
        return this.$t("trade_recharge_channel_paypal");
      }

      if (channel === RECHARGE_CHANNEL.FAWRY) {
        return this.$t("trade_recharge_channel_fawry");
      }

      return "";
    },
    rechargeText() {
      return this.isThirdPartyCompensation ? this.$t('trade_recharge_coins_compensation') : this.$t('common_recharge')
    },
    timeText() {
      return moment(this.record.createTime).format("YYYY.MM.DD HH:mm");
    },
    isSuccess() {
      const status = this.record.status;
      return status === RECHARGE_STATUS.RECHARGE_SUCCESS;
    },
    statusText() {
      const status = this.record.status;
      if (
        status === RECHARGE_STATUS.RECHARGING ||
        status === RECHARGE_STATUS.REVIEWING
      ) {
        return this.$t("common_waiting");
      }

      if (status === RECHARGE_STATUS.RECHARGE_SUCCESS) {
        return this.$t("common_success");
      }

      if (
        status === RECHARGE_STATUS.RECHARGE_FAILED ||
        status === RECHARGE_STATUS.REVIEW_REJECTED
      ) {
        return this.$t("common_failed");
      }

      return "";
    },
    statusStyle() {
      const status = this.record.status;
      if (
        status === RECHARGE_STATUS.RECHARGING ||
        status === RECHARGE_STATUS.REVIEWING
      ) {
        return {
          color: "#ff9100",
        };
      }

      if (status === RECHARGE_STATUS.RECHARGE_SUCCESS) {
        return {
          color: "#00e0d7",
        };
      }

      if (
        status === RECHARGE_STATUS.RECHARGE_FAILED ||
        status === RECHARGE_STATUS.REVIEW_REJECTED
      ) {
        return {
          color: "#fe1b58",
        };
      }

      return {};
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include v-layout(record-item, flex-start) {
  width: 100%;
  padding: 0px 32px;
  background-color: $--color-white;
  @include h-layout(title) {
    width: 100%;
    height: 88px;
    justify-content: space-between;

    @include text-view(
      recharge-way,
      $font-size: 30px,
      $line-height: 36px,
      $font-color: $--color-222222,
      $font-weight: $--font-weight-regular
    );

    @include text-view(
      recharge-amount,
      $font-size: 34px,
      $line-height: 40px,
      $font-color: $--color-25252F,
      $font-weight: $--font-weight-bold
    );
  }

  @include divide-line(h, 686px, 1px, $--color-E9E9EA);

  .margin-top-24px {
    margin-top: 24px;
  }

  @include h-layout(date-status) {
    width: 100%;
    height: 77px;
    justify-content: space-between;
    @include text-view(
      date,
      $font-size: 24px,
      $line-height: 29px,
      $font-color: $--color-AAAAAA,
      $font-weight: $--font-weight-regular
    );
    @include text-view(
      status,
      $font-size: 24px,
      $line-height: 29px,
      $font-color: $--color-AAAAAA,
      $font-weight: $--font-weight-regular
    );
  }
}
</style>