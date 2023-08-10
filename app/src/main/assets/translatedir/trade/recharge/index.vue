<template>
  <page
    bgColor="#FFFFFF"
    contentBGColor="#FFFFFF"
    :navigation="{ title: $t('trade_recharge'), endIcons: navigationEndIcons }"
    @on-end-icon-click="onNavEndIconClick($event)"
  >
    <recharge-body :showSalaryAdvanceRecharge="false"></recharge-body>
  </page>
</template>
<script>
import Page from "@/common/components/Page.vue";
import RechargeBody from "@/trade/recharge/RechargeBody";
import { hideNavigationBar } from "../../../common/bridge/index";

const NAVIGATION_END_ICON_ACTION = {
  HISTORY: 0,
};

export default {
  name: "TradeRecharge",
  components: {
    page: Page,
    "recharge-body": RechargeBody,
  },
  created() {
    hideNavigationBar();
  },
  data() {
    return {
      navigationEndIcons: [
        {
          action: NAVIGATION_END_ICON_ACTION.HISTORY,
          icon: require("@/common/assets/common_history_black_48_ic.png"),
        },
      ],
    }
  },
  methods: {
    onNavEndIconClick(action) {
      if (action === NAVIGATION_END_ICON_ACTION.HISTORY) {
        this.$router.push("/trade/recharge/history");
      }
    },
  }
};
</script>
<style lang="scss" scoped>

</style>