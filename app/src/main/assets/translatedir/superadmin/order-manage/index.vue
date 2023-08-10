<template>
  <page
    bgColor="#FFFFFF"
    contentBGColor="#FFFFFF"
    :navigation="{ title: $t('super_admin_order_manage') }"
  >
    <div class="go-user-penalty-h-layout" @click="onGoUserPenaltyClick">
      <div class="remain-amount-v-layout">
        <span class="user-penalty-text-view">
          {{ $t("super_admin_user_penalty") }}
        </span>
        <span class="remain-amount-text-view">
          {{
            $t("super_admin_user_penalty_remain_normal_amount", {
              count: remainNormalPunishCount,
            })
          }}
        </span>
        <span class="remain-amount-text-view">
          {{
            $t("super_admin_user_penalty_remain_severe_amount", {
              count: remainSeverePunishCount,
            })
          }}
        </span>
      </div>
      <van-image
        class="go-arrow-image-view"
        :src="require('./assets/super_admin_order_managa_go_arrow_ic.png')"
      />
    </div>
    <div class="punishment-record-divide-line" />
    <span class="punishment-record-text-view">
      {{ $t("super_admin_punishment_record") }}
    </span>
    <van-list
      class="list-v-layout"
      v-model="loading"
      :finished="finished"
      finished-text=""
    >
      <punishment-record-item-view
        v-for="(item, index) in punishmentRecords"
        :record="item"
        :key="index"
      />
    </van-list>
    <van-popup v-model="showResponsibilityPopup" round>
      <common-dialog
        :titleText="$t('common_notice')"
        :messageText="$t('super_admin_responsibility_tip')"
        :primaryButtonText="$t('common_ok')"
        @on-primary-button-click="onResponsibilityDialogOKClick"
      />
    </van-popup>
  </page>
</template>
<script>
import Vue from "vue";
import { Image as VanImage, List, Popup } from "vant";
import Page from "@/common/components/Page.vue";
import PunishmentRecordItemView from "@/superadmin/order-manage/components/PunishmentRecordItemView";
import { mapActions, mapState } from "vuex";
import {
  hideNavigationBar,
  dispatchDeeplink,
} from "../../../common/bridge/index";
import {
  addNativeMessageListener,
  removeNativeMessageListener,
} from "../../../common/bridge/base";
import CommonDialog from "@/common/components/CommonDialog";
import { SUPER_ADMIN_RESPONSIBILITY_GUIDE } from "../storage/key";
import { MSG_TYPE } from "../../../common/bridge/constant";

Vue.use(VanImage);
Vue.use(List);
Vue.use(Popup);

export default {
  name: "OrderManage",
  components: {
    page: Page,
    "punishment-record-item-view": PunishmentRecordItemView,
    "common-dialog": CommonDialog,
  },
  created() {
    hideNavigationBar();
    let self = this;
    addNativeMessageListener(MSG_TYPE.ON_RESUME, (_) => {
      self.getPunishmentRecords();
    });
    this.showResponsibilityDialog();
    this.getPunishmentRecords();
  },
  destroyed() {
    removeNativeMessageListener(MSG_TYPE.ON_RESUME);
  },
  data() {
    return {
      loading: false,
      finished: true,
      showResponsibilityPopup: false,
    };
  },
  methods: {
    ...mapActions("superadmin", ["getPunishmentRecords"]),
    onGoUserPenaltyClick() {
      dispatchDeeplink({
        deeplink: `wenext://wayak/super_admin/user_penalty?extra_remain_penalty_count=${this.remainNormalPunishCount + this.remainSeverePunishCount}`,
        path: "/super_admin/user_penalty",
      });
    },
    showResponsibilityDialog() {
      if (this.$localStorage.get(SUPER_ADMIN_RESPONSIBILITY_GUIDE, false)) {
        return;
      }

      this.$localStorage.set(SUPER_ADMIN_RESPONSIBILITY_GUIDE, true);
      this.showResponsibilityPopup = true;
    },
    onResponsibilityDialogOKClick() {
      this.showResponsibilityPopup = false;
    },
  },
  computed: {
    ...mapState("superadmin", {
      remainNormalPunishCount: (state) => state.remainNormalPunishCount,
      remainSeverePunishCount: (state) => state.remainSeverePunishCount,
      punishmentRecords: (state) => state.punishmentRecords,
    }),
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include h-layout(go-user-penalty) {
  width: 686px;
  height: 160px;
  justify-content: space-between;
  background: #00e0d7;
  border-radius: 24px;
  @include compat.padding-start(30px);
  @include compat.padding-end(30px);
  margin-top: 24px;

  @include v-layout(remain-amount, flex-start) {
    @include text-view(
      user-penalty,
      $font-size: 32px,
      $line-height: 38px,
      $font-color: #ffffff,
      $font-weight: $--font-weight-bold,
      $line-number: 1
    );
    @include text-view(
      remain-amount,
      $font-size: 26px,
      $line-height: 31px,
      $font-color: #ffffff,
      $line-number: 1
    ) {
      margin-top: 12px;
    }
  }

  @include image-view(go-arrow, 48px, 48px) {
    @include dir {
      transform: rotateY(180deg);
    }
  }
}

@include divide-line(punishment-record, 750px, 16px, #f5f7fa) {
  margin-top: 32px;
}

@include text-view(
  punishment-record,
  $font-size: 28px,
  $line-height: 80px,
  $font-color: #222222,
  $font-weight: $--font-weight-bold,
  $line-number: 1
) {
  @include compat.padding-start(30px);
  @include compat.padding-end(30px);
  align-self: flex-start;
}

@include v-layout(list) {
  width: 100%;
}
</style>