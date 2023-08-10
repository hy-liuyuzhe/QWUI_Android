<template>
  <div class="count-down-container">
    <span class="count-down">{{ this.$t("common_count_down") }}</span>

    <template v-if="showMonth">
      <div class="count-down-first-item-container">
        <van-image class="count-down-num-bg-img" :src="countdownNumBgImg" />
        <span class="count-down-field">{{ countdownTime.month }}</span>
      </div>
      <span class="count-down-num-title">{{ this.$t("common_month") }}</span>
    </template>

    <template v-if="showDay">
      <div class="count-down-other-item-container">
        <van-image class="count-down-num-bg-img" :src="countdownNumBgImg" />
        <span class="count-down-field">{{ countdownTime.day }}</span>
      </div>
      <span class="count-down-num-title">{{ this.$t("common_day") }}</span>
    </template>

    <template v-if="showHour">
      <div class="count-down-other-item-container">
        <van-image class="count-down-num-bg-img" :src="countdownNumBgImg" />
        <span class="count-down-field">{{ countdownTime.hour }}</span>
      </div>
      <span class="count-down-num-title">{{ this.$t("common_hour") }}</span>
    </template>

    <template v-if="showMinute">
      <div class="count-down-other-item-container">
        <van-image class="count-down-num-bg-img" :src="countdownNumBgImg" />
        <span class="count-down-field">{{ countdownTime.min }}</span>
      </div>
      <span class="count-down-num-title">{{ this.$t("common_min") }}</span>
    </template>

    <template v-if="showSecond">
      <div class="count-down-other-item-container">
        <van-image class="count-down-num-bg-img" :src="countdownNumBgImg" />
        <span class="count-down-field">{{ countdownTime.second }}</span>
      </div>
      <span class="count-down-num-title">{{ this.$t("common_second") }}</span>
    </template>
  </div>
</template>
<script>
import Vue from "vue";
import { Image as VanImage } from "vant";
import moment from "moment";
import { DATE_UNIT } from "../constant/index";

Vue.use(VanImage);
export default {
  name: "CountDownItem",
  data() {
    return {
      leftTime: 0,
      lastEndTime: 0,
      countdownTimer: null,
    };
  },
  props: {
    countdownNumBgImg: String,
    endTime: Number,
    type: Number,
    minDateUnit: Number,
    maxDateUnit: Number,
  },
  created() {
    this.lastEndTime = this.endTime;
    this.startCoundDown();
  },
  watch: {
    endTime: function (data) {
      if (data != this.lastEndTime) {
        this.lastEndTime = data;
        this.startCoundDown();
      }
    },
  },
  computed: {
    countdownTime() {
      const time = moment.duration(this.leftTime, "seconds");
      const month = time.months();
      const day = time.days();
      const hour = time.hours();
      const min = time.minutes();
      const second = time.seconds();
      return { month, day, hour, min, second };
    },
    showMonth() {
      return this.maxDateUnit >= DATE_UNIT.MONTH;
    },
    showDay() {
      return (
        this.maxDateUnit >= DATE_UNIT.DAY && this.minDateUnit <= DATE_UNIT.DAY
      );
    },
    showHour() {
      return (
        this.maxDateUnit >= DATE_UNIT.HOUR && this.minDateUnit <= DATE_UNIT.HOUR
      );
    },
    showMinute() {
      return (
        this.maxDateUnit >= DATE_UNIT.MINUTE &&
        this.minDateUnit <= DATE_UNIT.MINUTE
      );
    },
    showSecond() {
      return (
        this.maxDateUnit >= DATE_UNIT.SECOND &&
        this.minDateUnit <= DATE_UNIT.SECOND
      );
    },
  },
  methods: {
    countdown() {
      if (this.leftTime > 0) {
        this.leftTime = this.leftTime - 1;
      } else {
        this.cancelCoundDown();
      }
    },
    startCoundDown() {
      this.cancelCoundDown();
      if (this.endTime) {
        if (moment().unix() < this.endTime) {
          this.leftTime = this.endTime - moment().unix();
          this.countdownTimer = setInterval(() => {
            this.countdown();
          }, 1000);
        }
      }
    },
    cancelCoundDown() {
      if (this.countdownTimer) {
        clearInterval(this.countdownTimer);
        this.countdownTimer = null;
      }
    },
  },
  destroyed() {
    this.cancelCoundDown();
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
.count-down-container {
  height: 40px;
  display: flex;
  flex-direction: row;
  align-items: center;
}
.count-down {
  font-size: 24px;
  font-family: SFUIText-Medium, SFUIText;
  font-weight: 500;
  color: #ffffff;
  line-height: 29px;
}
.count-down-item-container {
  width: 40px;
  height: 40px;
  display: flex;
  flex-direction: column;
}
.count-down-field {
  width: 40px;
  height: 40px;
  font-size: 26px;
  font-family: SFUIText-Medium, SFUIText;
  font-weight: 500;
  color: #f6ff65;
  line-height: 40px;
  margin-top: -40px;
}
.count-down-first-item-container {
  @extend .count-down-item-container;
  @include compat.margin-start(16px);
}
.count-down-other-item-container {
  @extend .count-down-item-container;
  @include compat.margin-start(8px);
}
.count-down-num-bg-img {
  width: 40px;
  height: 40px;
  z-index: 0;
}
.count-down-num-title {
  font-size: 24px;
  font-family: SFUIText-Medium, SFUIText;
  font-weight: 500;
  color: #ffffff;
  line-height: 29px;
  @include compat.margin-start(8px);
}
</style>