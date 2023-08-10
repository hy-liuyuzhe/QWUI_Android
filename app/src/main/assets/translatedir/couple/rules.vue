<template>
  <page
    bgColor="#FFFFFF"
    contentBGColor="#FFFFFF"
    :navigation="{ title: $t('couple_couple') }"
  >
    <div class="root-v-layout">
      <div class="intimacy-v-layout">
        <div class="intimacy-title-h-layout">
          <span class="number-text-view">01</span>
          <span class="intimacy-level-title-text-view">{{
            $t("couple_what_is_couple_level")
          }}</span>
        </div>
        <span class="rules-desc-text-view">{{ $t("couple_intimacy_1") }}</span>
        <span class="rules-desc-text-view">{{ $t("couple_intimacy_2") }}</span>
        <span class="rules-desc-text-view">{{ $t("couple_intimacy_3") }}</span>
        <span class="rules-desc-text-view">{{ $t("couple_intimacy_4") }}</span>
        <table>
          <thead>
            <tr>
              <th>{{ $t("couple_level") }}</th>
              <th>{{ $t("couple_need_intimacy") }}</th>
              <th>{{ $t("couple_intimacy_daily_limit") }}</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(config, i) in configs" :key="i">
              <td>LV{{ config.level }}</td>
              <td>{{ config.exp }}</td>
              <td>{{ getLimitDesc(config.expDailyLimit) }}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <div class="privilege-v-layout">
        <div class="privilege-title-h-layout">
          <span class="number-text-view">02</span>
          <span class="privilege-title-text-view">{{
            $t("couple_how_many_privilege")
          }}</span>
        </div>
        <span class="rules-desc-text-view">{{
          $t("couple_privilege_desc")
        }}</span>
        <table>
          <thead>
            <tr>
              <th>{{ $t("couple_level") }}</th>
              <th>{{ $t("couple_background") }}</th>
              <th>{{ $t("couple_effect") }}</th>
            </tr>
          </thead>
          <tbody tr v-for="(config, i) in configs" :key="i">
            <tr>
              <td>LV{{ config.level }}</td>
              <td>{{ $t("couple_cover_level", { level: config.level }) }}</td>
              <td>{{ getEffectDesc(config) }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </page>
</template>
<script>
import Page from "@/common/components/Page.vue";
import api from "./api/index";
import { hideNavigationBar } from "../../common/bridge/index";

export default {
  name: "CoupleRules",
  data() {
    return {
      configs: [],
    };
  },
  created() {
    hideNavigationBar();
    this.getCpConfigs();
  },
  components: {
    page: Page,
  },
  methods: {
    async getCpConfigs() {
      const res = await api.getCoupleConfig();
      const { code, data } = res;
      if (code == 200) {
        var configs = new Array();
        const map = new Map(Object.entries(data));
        for (var i = 0; i < map.size; i++) {
          configs.push(data[`${i + 1}`]);
        }
        this.configs = configs;
      }
    },
    getLimitDesc(limit) {
      if (limit == 0) {
        return this.$t("couple_unlimit");
      } else {
        return limit;
      }
    },
    getEffectDesc(config) {
      if (config.onMicEffect == 1) {
        return this.$t("couple_onmic_effect");
      } else if (config.onlineRemind == 1) {
        return this.$t("couple_online_notify");
      } else {
        return "";
      }
    },
  },
  metaInfo: {
    title: "CP",
    meta: [
      {
        name: "viewport",
        content: "width=device-width, initial-scale=1, maximum-scale=1",
      },
    ],
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";
@include v-layout(root) {
  width: 750px;
  @include v-layout(intimacy) {
    width: 702px;
    background: #fef6f7;
    border-radius: 30px;
    padding: 33px 0px 48px 0px;
    text-align: start;
    @include h-layout(intimacy-title, flex-start) {
      width: 100%;
      @include text-view(
        intimacy-level-title,
        $font-size: 32px,
        $line-height: 45px,
        $font-color: #222222,
        $font-weight: $--font-weight-bold
      ) {
        width: 566px;
        text-align: start;
        @include compat.margin-start(16px);
      }
    }
  }
  @include text-view(
    rules-desc,
    $font-size: 28px,
    $line-height: 40px,
    $font-color: #666666,
    $font-weight: $--font-weight-regular
  ) {
    width: 622px;
    margin-top: 12px;
    @include compat.margin-start(40px);
  }
  @include text-view(
    number,
    $font-size: 38px,
    $line-height: 53px,
    $font-color: #ec395a,
    $font-weight: $--font-weight-bold,
    $line-number: 1
  ) {
    width: 60px;
    text-align: center;
    @include compat.margin-start(20px);
  }
  @include v-layout(privilege) {
    width: 702px;
    background: #fef6f7;
    border-radius: 30px;
    padding: 33px 0px 48px 0px;
    text-align: start;
    margin-top: 32px;
    @include h-layout(privilege-title, flex-start) {
      width: 100%;
      @include text-view(
        privilege-title,
        $font-size: 32px,
        $line-height: 45px,
        $font-color: #222222,
        $font-weight: $--font-weight-bold
      ) {
        width: 566px;
        text-align: start;
        @include compat.margin-start(16px);
      }
    }
  }
}
table {
  width: 623px;
  text-align: center;
  font-size: 25px;
  margin-top: 30px;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  border-collapse: collapse;
  border: 1px solid #ffd2e0;
  border-radius: 20px;
  border-style: hidden;
  box-shadow: 0 0 0 1px #ffd2e0;
}
table thead tr th {
  background: #ffd2e0;
  height: 80px;
}
table thead tr:first-child th:first-child {
  border-top-left-radius: 20px;
  border-right: 1px solid #ffffff;
  @include dir {
    border-top-right-radius: 20px;
    border-top-left-radius: unset;
    border-left: 1px solid #ffffff;
  }
}
table thead tr:first-child th:last-child {
  border-top-right-radius: 20px;
  border-left: 1px solid #ffffff;
  @include dir {
    border-top-left-radius: 20px;
    border-top-right-radius: unset;
    border-right: 1px solid #ffffff;
  }
}
table tbody tr td {
  border-left: 1px solid #e3e2e2;
  border-right: 1px solid #e3e2e2;
  border-bottom: 1px solid #e3e2e2;
  min-width: 100px;
  height: 60px;
}
</style>
