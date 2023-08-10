<template>
  <div class="country-select-v-layout">
    <span class="country-select-text-view">{{
      $t("common_choose_country")
    }}</span>
    <div class="search-h-layout">
      <img class="search-icon" src="../assets/common_search_ic.png" />
      <input
        v-model="searchKeywork"
        type="text"
        class="search-edit-view"
        :placeholder="$t('common_search')"
        @input="searchInput"
      />
    </div>
    <div class="country-list-v-layout">
      <div
        class="country-item-h-layout"
        v-for="(item, index) in countryList"
        :key="index"
        @click="onCountryItemClick(item)"
      >
        <van-image class="nation-flag-img" :src="item.nationFlag"></van-image>
        <span class="country-name-text-view">{{ item.countryName }}</span>
        <span class="area-code-text-view">{{ item.areaCode }}</span>
      </div>
    </div>
  </div>
</template>
<script>
import Vue from "vue";
import { Image as VanImage } from "vant";
import { getCountryAreaCode } from "../../../common/locale/index";
import { COUNTRY_CODE_TO_NATION_FLAG } from "../constant/countrySelect";
import _ from "lodash";

Vue.use(VanImage);
export default {
  name: "CountrySelect",
  data() {
    return {
      searchKeywork: "",
      allCountryList: [],
      countryList: [],
    };
  },
  created() {
    this.initCountryList();
  },
  methods: {
    initCountryList() {
      var countryList = [];
      var self = this;
      _.forEach(COUNTRY_CODE_TO_NATION_FLAG, function(value, key) {
        countryList.push({
          countryName: self.$t(`${key}`),
          nationFlag: value,
          areaCode: `+${getCountryAreaCode(key)}`,
        });
      });
      this.allCountryList = countryList;
      this.countryList = countryList;
    },
    searchInput() {
      if (_.isEmpty(this.searchKeywork)) {
        this.countryList = this.allCountryList;
        return;
      }
      let lowerCaseKeyWork = this.searchKeywork.toLocaleLowerCase();
      console.log("lower:", lowerCaseKeyWork);
      var searchList = [];
      _.forEach(this.allCountryList, function(item) {
        if (
          item.countryName.toLocaleLowerCase().includes(lowerCaseKeyWork) ||
          item.areaCode.includes(lowerCaseKeyWork)
        ) {
          searchList.push(item);
        }
      });
      this.countryList = searchList;
    },
    onCountryItemClick(item) {
      this.$emit("on-country-select", item);
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";
@include v-layout("country-select") {
  width: 750px;
  height: 75vh;
  background: white;
  @include text-view(
    country-select,
    $font-size: 36px,
    $line-height: 40px,
    $font-color: #333333,
    $font-weight: $--font-weight-bold
  ) {
    width: 100%;
    padding: 30px 30px 0px 30px;
  }
  @include h-layout("search") {
    width: 690px;
    background-color: #f1f1f2;
    border-radius: 10px;
    padding: 10px;
    margin-top: 40px;
    .search-icon {
      width: 32px;
      height: 32px;
      @include compat.margin-start(20px);
    }
    @include edit-view(
      search,
      $width: 100%,
      $height: 64px,
      $font-size: 32px,
      $font-color: #333333,
      $font-weight: $--font-weight-regular,
      $background-color: $--color-transparent
    ) {
      padding: 0px 16px;
    }
  }
  @include v-layout(country-list) {
    width: 100%;
    overflow: scroll;
    scrollbar-width: none;
    padding: 0px 30px;
    @include h-layout(country-item) {
      width: 100%;
      min-height: 120px;
      .nation-flag-img {
        width: 48px;
        height: 32px;
      }
      @include text-view(
        country-name,
        $font-size: 32px,
        $line-height: 36px,
        $font-color: #333333,
        $font-weight: $--font-weight-medium,
        $line-number: 1
      ) {
        width: 100%;
        @include compat.margin-start(30px);
      }
      @include text-view(
        area-code,
        $font-size: 28px,
        $line-height: 32px,
        $font-color: #333333,
        $font-weight: $--font-weight-regular
      ) {
        @include compat.margin-start(30px);
      }
    }
  }
}
</style>
