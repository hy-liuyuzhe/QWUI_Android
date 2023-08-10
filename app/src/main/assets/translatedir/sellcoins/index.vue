<template>
  <div class="root-container">
    <navigation-bar
      class="navigation-bar"
      :type="navigationStyle"
      :title="navigationTitle"
      :endIcons="navigationEndIcons"
      @on-back-click="onBackClick($event)"
      @on-end-icon-click="onEndIconClick($event)"
    />
    <van-tabs
      v-model="active"
      color="#00E0D7"
      title-active-color="#222222"
      title-inactive-color="#AAAAAA"
      line-height="3px"
      line-width="32px"
      sticky
      :swipeable="true"
    >
      <van-tab :title="$t('sell_coins')"><sell-coins /></van-tab>
      <van-tab :title="$t('sell_vips')"><sell-vips /></van-tab>
      <van-tab :title="$t('trade_recharge')"><recharge :showSalaryAdvanceRecharge="false"/></van-tab>
    </van-tabs>
    <van-popup v-model="showFillWhatsappDialog" get-container="body" round>
      <common-dialog
        :titleText="editingWhatsAppDialogTitle"
        :messageText="$t('merchat_fill_whatsapp_tip')"
        :primaryButtonText="editingWhatsAppDialogPositiveBtnText"
        :secondaryButtonText="$t('common_cancel')"
        @on-primary-button-click="onConfirmWhatsappClick"
        @on-secondary-button-click="onCancelWhatsappClick"
      >
        <input
          v-model="editingWhatsapp"
          class="fill-whatsapp-edit-view"
          type="text"
        />
      </common-dialog>
    </van-popup>
    <van-popup v-model="showMerchantReportedDialog" get-container="body" round>
      <common-dialog
        :titleText="$t('common_warn')"
        :messageText="merchantReportedMessage"
        :primaryButtonText="$t('common_confirm')"
        @on-primary-button-click="onConfirmMerchantMsgClick"
      >
        <template v-slot:top-img>
          <van-image
            class="warning-image-view"
            :src="require('../common/assets/common_warning_ic.png')"
          />
        </template>
      </common-dialog>
    </van-popup>
  </div>
</template>
<script>
import _ from "lodash";
import NavigationBar from "@/common/components/NavigationBar";
import { hideNavigationBar, closeWindow } from "../../common/bridge/index";
import { NAVIGATION_BAR_STYLE } from "../common/constant/index";
import CommonDialog from "@/common/components/CommonDialog";
import Vue from "vue";
import { Tab, Tabs, Popup, Toast } from "vant";
import SellCoins from "./components/SellCoins";
import SellVips from "./components/SellVips";
import Recharge from '../trade/recharge/RechargeBody'
import api from "./api/index";
import { RES_COMMON_CODE } from "../../common/network/constant";
import { MerchantMessage, MERCHANT_MESSAGE_TYPE } from "./constant/index";

Vue.use(Tab);
Vue.use(Tabs);
Vue.use(Popup);
Vue.use(Toast);
const NAVIGATION_END_ICON_ACTION = {
  WHATSAPP: 0,
};
export default {
  name: "Merchant",
  components: {
    "navigation-bar": NavigationBar,
    "sell-coins": SellCoins,
    "sell-vips": SellVips,
    "common-dialog": CommonDialog,
    "recharge": Recharge,
  },
  created() {
    hideNavigationBar();
    this.getMyMerchantInfo();
    this.getMerchantMessage();
  },
  data() {
    return {
      navigationStyle: NAVIGATION_BAR_STYLE.DRAK,
      navigationTitle: this.$t("sell_coins"),
      navigationEndIcons: [
        {
          action: NAVIGATION_END_ICON_ACTION.WHATSAPP,
          icon: require("./assets/sell_coin_whatsapp_ic.png"),
        },
      ],
      anchorTasks: null,
      active: 0,
      showFillWhatsappDialog: false,
      whatsapp: "",
      editingWhatsapp: "",
      editingWhatsAppDialogTitle: "",
      editingWhatsAppDialogPositiveBtnText: "",
      merchantReportedMessage: "",
      showMerchantReportedDialog: false,
      repoartedMessage: null,
    };
  },
  methods: {
    onBackClick() {
      window.history.length > 1 ? this.$router.go(-1) : closeWindow();
    },
    onEndIconClick(action) {
      if (action === NAVIGATION_END_ICON_ACTION.WHATSAPP) {
        if (_.isEmpty(this.whatsapp)) {
          this.showSetWhatsappDialog();
        } else {
          this.showModifyWhatsappDialog();
        }
      }
    },
    async getMyMerchantInfo() {
      const { code, data } = await api.getMyMerchantInfo();
      if (code === RES_COMMON_CODE.SUCCESS) {
        if (_.isEmpty(data.whatsapp)) {
          this.showSetWhatsappDialog();
        } else {
          this.whatsapp = data.whatsapp;
        }
      }
    },
    async getMerchantMessage() {
      const { code, data } = await api.getAnchorMessage();
      if (code === RES_COMMON_CODE.SUCCESS) {
        let messageIndex = _.findIndex(data, (item) => {
          return item.messageType === MERCHANT_MESSAGE_TYPE;
        });
        if (messageIndex === -1) {
          return;
        }
        let message = data[messageIndex];
        this.repoartedMessage = message;
        if (_.isEmpty(message.messageBody)) {
          return;
        }
        var messageBody = JSON.parse(message.messageBody);
        if (_.isEmpty(messageBody)) {
          return;
        }
        var msgContent = "";
        if (messageBody.type === MerchantMessage.warning) {
          msgContent = this.$t("merchant_warning_low_sell");
        } else if (messageBody.type === MerchantMessage.cancelDays) {
          msgContent = this.$t("merchant_cancel_days");
        } else {
          msgContent = this.$t("merchant_cancel_forever");
        }
        if (_.isEmpty(msgContent)) {
          return;
        }
        this.merchantReportedMessage = msgContent;
        this.showMerchantReportedDialog = true;
      }
    },
    showSetWhatsappDialog() {
      this.editingWhatsAppDialogTitle = this.$t("merchant_my_whatsapp");
      this.editingWhatsAppDialogPositiveBtnText = this.$t("common_finish");
      this.editingWhatsapp = "";
      this.showFillWhatsappDialog = true;
    },
    showModifyWhatsappDialog() {
      this.editingWhatsAppDialogTitle = this.$t("merchant_modify_my_whatsapp");
      this.editingWhatsAppDialogPositiveBtnText = this.$t("common_revise");
      this.editingWhatsapp = this.whatsapp;
      this.showFillWhatsappDialog = true;
    },
    async onConfirmWhatsappClick() {
      if (this.editingWhatsapp === "") {
        Toast(this.$t("common_not_input", { name: "Whatsapp" }));
        return;
      }
      this.showFillWhatsappDialog = false;
      if (this.editingWhatsapp === this.whatsapp) {
        return;
      }
      const { code, _ } = await api.updateMerchantInfo({
        whatsapp: this.editingWhatsapp,
      });
      if (code === RES_COMMON_CODE.SUCCESS) {
        Toast(this.$t("common_success"));
        this.whatsapp = this.editingWhatsapp;
      } else {
        Toast(this.$t("common_failed_with_errorcode", { code: code }));
      }
    },
    onCancelWhatsappClick() {
      this.showFillWhatsappDialog = false;
    },
    onConfirmMerchantMsgClick() {
      this.showMerchantReportedDialog = false;
      api.replyAnchorMessage({
        messageId: this.repoartedMessage.messageId,
        messagetype: 6,
        replyCode: 1,
      });
    },
  },
  metaInfo: {
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
@use "@/common/css/text.scss" as text;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";
@include edit-view(
  fill-whatsapp,
  $width: 100%,
  $height: 96px,
  $font-size: 28px,
  $font-color: $--color-777777,
  $font-weight: $--font-weight-regular,
  $background-color: $--color-F5F7FA
) {
  border-radius: 24px;
  padding: 24px 48px;
  margin-top: 15px;
}
@include image-view(warning, 180px, 160px);
</style>
