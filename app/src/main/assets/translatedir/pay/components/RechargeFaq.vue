<template>
  <div class="faq-page">
    <p class="content-title">{{ $t("what_is_gold_coin") }}</p>
    <p class="content-desc">{{ $t("gold_coin_desc") }}</p>
    <p class="content-title">{{ $t("how_to_recharge") }}</p>
    <p class="content-desc">{{ $t("how_to_recharge_desc") }}</p>
    <template v-if="hightLevelShow">
      <p class="content-desc">{{ $t("use_method") }}</p>
      <p class="content-desc">{{ $t("method_1") }}</p>
      <p class="content-desc">{{ $t("method_2") }}</p>
      <p class="content-desc">{{ $t("method_3") }}</p>
      <p class="content-desc">{{ $t("method_4") }}</p>
    </template>
    <p class="content-title">{{ $t("recharge_no_success") }}</p>
    <p class="content-desc">{{ $t("recharge_no_success_desc_1") }}</p>
    <p class="content-desc">{{ $t("recharge_no_success_desc_2") }}</p>
    <p class="content-desc">{{ $t("recharge_no_success_desc_3") }}</p>
  </div>
</template>
<script>
import { getMyLevel } from "../../../common/bridge/index";

export default {
  name: "RechargeFaq",
  data() {
    return {
      hightLevelShow: false,
    };
  },
  created() {
    this.getHightLevelShow();
  },
  methods: {
    async getHightLevelShow() {
      const level = await getMyLevel();
      this.hightLevelShow = level >= 5;
    },
  },
};
</script>
<style lang="scss" scoped>
.faq-page {
  padding-left: 40px;
  padding-right: 40px;
  padding-top: 40px;
  padding-bottom: 200px;
}
.content-title {
  font-size: 32px;
  font-family: SFUIText-Medium, SFUIText;
  font-weight: 500;
  color: #222222;
  margin: 0px;
}
.content-desc {
  font-size: 28px;
  font-family: SFUIText-Regular, SFUIText;
  font-weight: 400;
  color: #777777;
  line-height: 34px;
}
</style>