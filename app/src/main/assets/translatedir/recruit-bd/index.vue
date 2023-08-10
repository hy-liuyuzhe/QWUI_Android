<template>
  <div class="we-page">
    <van-image class="top-bg-image-view" :src="topBgImgSrc" />
    <div class="content-v-layout">
      <div class="rule-title-v-layout">
        <div class="rule-title-content-v-layout">
          <span class="rule-title-text-view">{{ $t("recruit_bd_rule") }}</span>
        </div>
      </div>
      <div class="rule-content-v-layout">
        <div class="rule-content-info-v-layout">
          <span class="rule-content-info-text-view">
            {{ $t("recruit_bd_rule_info") }}
          </span>
        </div>
        <div class="rule-content-table-v-layout">
          <table>
            <thead>
              <tr>
                <th>{{ $t("recruit_bd_number_of_guilds") }}</th>
                <th>{{ $t("recruit_bd_bonus") }}</th>
                <th></th>
                <th>{{ $t("recruit_bd_commission") }}</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>1</td>
                <td>20$</td>
                <td rowspan="5">+</td>
                <td rowspan="5">{{ $t("recruit_bd_guild_salary") }}</td>
              </tr>
              <tr>
                <td>2</td>
                <td>40$</td>
              </tr>
              <tr>
                <td>3</td>
                <td>100$</td>
              </tr>
              <tr>
                <td>4</td>
                <td>150$</td>
              </tr>
              <tr>
                <td>5</td>
                <td>200$</td>
              </tr>
            </tbody>
          </table>
          <div class="rule-content-item-h-layout">
            <div class="item-logo-v-layout">
              <van-image class="item-logo-image-view" :src="require('./assets/recruit_bd_rule_item_logo.png')" />
            </div>
            <div class="item-info-v-layout">
              <span class="item-info-text-view">
                {{ $t("recruit_bd_rule_1") }}
              </span>
            </div>
          </div>
          <div class="rule-content-item-h-layout">
            <div class="item-logo-v-layout">
              <van-image class="item-logo-image-view" :src="require('./assets/recruit_bd_rule_item_logo.png')" />
            </div>
            <div class="item-info-v-layout">
              <span class="item-info-text-view">
                {{ $t("recruit_bd_rule_2") }}
              </span>
            </div>
          </div>
          <div class="rule-content-item-h-layout">
            <div class="item-logo-v-layout">
              <van-image class="item-logo-image-view" :src="require('./assets/recruit_bd_rule_item_logo.png')" />
            </div>
            <div class="item-info-v-layout">
              <span class="item-info-text-view">
                {{ $t("recruit_bd_rule_3") }}
              </span>
            </div>
          </div>
        </div>
      </div>

    </div>
    <div class="join-btn-v-layout" @click="onsubmit">
      <div class="join-btn-bg-v-layout">
        <div class="join-btn-info-v-layout">
          <span class="join-btn-info-text-view">
            {{ $t("recruit_bd_want_to_pull_new") }}
          </span>
        </div>
      </div>

    </div>

  </div>
</template>

<script>
import Vue from "vue";
import { Image as VanImage, Toast, Popup } from "vant";
import { NAVIGATION_BAR_STYLE } from "@/common/constant/index";
import api from "./api/index";
import { RES_COMMON_CODE } from "./../../common/network/constant";

Vue.use(VanImage);
Vue.use(Toast);
Vue.use(Popup);

export default {
  name: "RecruitBd",
  components: {

  },
  data() {
    return {
      navigationStyle: NAVIGATION_BAR_STYLE.LIGHT,
      disableBtn: false,
    }
  },
  created() {
    this.getBdInfo();
  },
  methods: {
    async getBdInfo() {
      const {code,data} = await api.getBdInfo();
      if (code === RES_COMMON_CODE.SUCCESS) {
        if (data !== undefined && data !== '' && data !== null) {
          this.disableBtn = true;
        }
      }
    },
    onsubmit() {
      if (!this.disableBtn) {
        this.$router.push(
          `/recruit_bd/bd_info`
        );
      } else {
        Toast(this.$t("recruit_bd_submit_success_info"));
      }
    }
  },
  computed: {
    topBgImgSrc() {
      let languageCode = this.$i18n.locale;
      if (languageCode === "ar") {
        return require(`./assets/recruit_bd_top_bg_ar.png`);
      }

      return require(`./assets/recruit_bd_top_bg_en.png`);
    },
  }
}
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include we(page) {
  width: 100%;
  box-sizing: border-box;
  background: linear-gradient(0deg, #190f04 0%, #26190a 91%);
  @include image-view(top-bg, 750px, 646px) {
    position: absolute;
    top: 0;
    left: 0;
  }
  @include v-layout(content) {
    position: relative;
    width: 100%;
    padding-bottom: 46px;
    @include v-layout(rule-title) {
      position: absolute;
      top: 466px;
      width: 522px;
      height: 80px;
      background-image: url("./assets/recruit_bd_rule_bg.png");
      background-repeat: no-repeat;
      background-size: cover;
      @include v-layout(rule-title-content) {
        width: 89px;
        height: 48px;
        margin-top: 14px;
        @include text-view(
                rule-title,
            $font-size: 40px,
            $line-height: 48px,
            $font-color: #784E23,
            $font-weight: $--font-weight-bold,
            $text-align: center
        )
      }
    }

    @include v-layout(rule-content){
      width: 710px;
      height: 1109px;
      background: #4A3A30;
      border-radius: 16px;
      border: 2px solid #DCA66E;
      margin-top: 485px;

      @include v-layout(rule-content-info) {
        padding: 101px 40px 0;
        @include text-view(
                rule-content-info,
            $font-size: 28px,
            $line-height: 40px,
            $font-color: #FDEFCF,
            $font-weight: $--font-weight-medium,
            $text-align: left
        )
      }

      @include v-layout(rule-content-table) {
        padding: 10px 40px;
      }

      @include h-layout(rule-content-item) {
        padding-top: 20px;
        width: 640px;
        align-items: flex-start;
        @include v-layout(item-logo) {
          padding-top: 10px;
          @include image-view(item-logo,20px,20px) {
            left: 0;
          }
        }
        @include v-layout(item-info) {
          padding-left: 20px;
          @include text-view(item-info,
          $font-size: 28px,
          $line-height: 40px,
          $font-color: #FDEFCF,
          $font-weight: $--font-weight-medium,
          $text-align: left
          )
        }
      }
    }
  }

  @include v-layout(join-btn) {
    position: sticky;
    position: -webkit-sticky;
    bottom: 20px;
    @include v-layout(join-btn-bg) {
      width: 686px;
      height: 106px;
      background-image: url("./assets/recruit_bd_join_btn.png");
      background-repeat: no-repeat;
      background-size: cover;

      @include v-layout(join-btn-info) {
        padding-top: 26px;
        @include text-view(
                join-btn-info,
            $font-size: 40px,
            $line-height: 48px,
            $font-color: #784E23,
            $font-weight: $--font-weight-bold,
            $text-align: center
        )
      }
    }
  }
}

table {
  width: 630px;
  text-align: center;
  font-size: 24px;
  font-family: PingFangSC-Medium, PingFang SC;
  font-weight: 500;
  color: #FDEFCF;
  line-height: 33px;
  border-collapse: collapse;
  box-shadow: 0 0 0 1px #DCA66E;
  background: #4A3A30;
  border-radius: 10px;
  border: 2px solid #DCA66E;
}
table thead tr th {
  background: #312824;
  min-width: 80px;
  padding: 10px;
  border-left: 1px solid #DCA66E;
  border-right: 1px solid #DCA66E;
}

table tbody tr td {
  border-left: 1px solid #DCA66E;
  border-right: 1px solid #DCA66E;
  border-bottom: 1px solid #DCA66E;
  border-top: 1px solid #DCA66E;
  height: 60px;
}
</style>