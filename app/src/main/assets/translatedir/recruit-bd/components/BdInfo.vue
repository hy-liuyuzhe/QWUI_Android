<template>
  <page
    bgColor="#F5F7FA"
    contentBGColor="#F5F7FA"
    :navigation="{ title: $t('recruit_bd_pull_new') }"
  >
    <div class="bd-info-v-layout">
      <title-layout
          class="margin-top-16px"
          :title="$t('recruit_bd_apps_info')"
          :note="$t('recruit_bd_required')"
      >
        <input
            v-model="apps"
            class="bd-info-edit-view"
            :placeholder="$t('recruit_bd_fill_out')"
        />
      </title-layout>
      <title-layout
          class="margin-top-16px"
          :title="$t('recruit_bd_upload_screenshots')"
          :note="$t('recruit_bd_required')"
      >
        <div class="margin-top-16px">
          <upload-file-add-view v-model="files" source="files" :maxCount="4" />
        </div>
      </title-layout>
      <title-layout
          class="margin-top-16px"
          :title="$t('recruit_bd_whatsapp')"
          :note="$t('recruit_bd_required')"
      >
        <input
            v-model="whatsapp"
            class="bd-info-edit-view"
            :placeholder="$t('recruit_bd_fill_out')"
        />
      </title-layout>
      <title-layout
          class="margin-top-16px"
          :title="$t('recruit_bd_reason')"
          :note="$t('recruit_bd_optional')"
      >
        <input
            v-model="reason"
            class="bd-info-edit-view"
            :placeholder="$t('recruit_bd_fill_out')"
        />
      </title-layout>
      <title-layout
          class="margin-top-16px"
          :title="$t('recruit_bd_how_to_do')"
          :note="$t('recruit_bd_optional')"
      >
        <input
            v-model="howToDo"
            class="bd-info-edit-view"
            :placeholder="$t('recruit_bd_fill_out')"
        />
      </title-layout>
      <title-layout
          class="margin-top-16px"
          :title="$t('recruit_bd_upload_cv')"
          :note="$t('recruit_bd_resume_format')"
      >
        <div class="margin-top-16px">
          <upload-file-add-view v-model="jd" source="jd" :maxCount="1" />
        </div>
      </title-layout>
      <span class="confirm-button" @click="onConfirmClick">
        {{ $t("recruit_bd_submit") }}
      </span>
      <van-overlay :show="showLoading">
        <div class="loading-view" @click.stop>
          <van-loading type="spinner" size="48px" />
        </div>
      </van-overlay>
    </div>
  </page>
</template>
<script>
import Vue from "vue";
import { Toast, Image as VanImage, Overlay, Loading } from "vant";
import Page from "@/common/components/Page.vue";
import { RES_COMMON_CODE } from "../../../common/network/constant";
import { hideNavigationBar } from "../../../common/bridge/index";
import UploadFileAddView from "@/common/components/UploadFileAddView";
import TitleLayout from "@/recruit-bd/components/TitleLayout";
import { uploadImgs } from "../../../common/oss/index";
import api from "../api/index";

Vue.use(Toast);
Vue.use(VanImage);
Vue.use(Overlay);
Vue.use(Loading);

export default {
  name: "BdInfo",
  components: {
    page: Page,
    "title-layout": TitleLayout,
    "upload-file-add-view": UploadFileAddView,
  },
  created() {
    hideNavigationBar();
  },
  data() {
    return {
      apps: "",
      whatsapp: "",
      reason: "",
      howToDo: "",
      files: [],
      jd: [],
      showLoading: false,
    };
  },
  methods: {
    async onConfirmClick() {
      if (this.apps === '' || this.whatsapp === '' || this.files.length === 0) {
        Toast(this.$t("recruit_bd_need_fill_required"));
        return ;
      }

      if (this.apps.length > 100) {
        const lengthMsg = this.$t("recruit_bd_limit_length", {
          length: 100,
        });
        Toast(this.$t("recruit_bd_question") + "1" + " " + lengthMsg);
        return ;
      }

      if (this.reason.length > 1000) {
        const lengthMsg = this.$t("recruit_bd_limit_length", {
          length: 1000,
        });
        Toast(this.$t("recruit_bd_question") + "4" + " " + lengthMsg);
        return ;
      }

      if (this.howToDo.length > 1000) {
        const lengthMsg = this.$t("recruit_bd_limit_length", {
          length: 1000,
        });
        Toast(this.$t("recruit_bd_question") + "5" + " " + lengthMsg);
        return ;
      }

      this.showLoading = true;
      const imageList = [];
      const jdList = [];
      const {code, data} = await uploadImgs(this.files);

      if (code !== RES_COMMON_CODE.SUCCESS) {
        Toast(this.$t("common_failed_with_errorcode", {code}));
        this.showLoading = false;
        return;
      }

      data.forEach((result) => {
        const {code, data} = result;
        if (code === RES_COMMON_CODE.SUCCESS) {
          imageList.push(data);
        } else {
          Toast(this.$t("common_upload_failed"));
          this.showLoading = false;
        }
      });

      if (this.jd.length > 0) {
        const {code, data} = await uploadImgs(this.jd);
        if (code !== RES_COMMON_CODE.SUCCESS) {
          Toast(this.$t("common_failed_with_errorcode", {code}));
          this.showLoading = false;
          return;
        }

        data.forEach((result) => {
          const {code, data} = result;
          if (code === RES_COMMON_CODE.SUCCESS) {
            jdList.push(data);
          } else {
            Toast(this.$t("common_upload_failed"));
            this.showLoading = false;
          }
        });
      }
      this.addBdInfo(JSON.stringify(imageList),jdList[0]);

    },
    async addBdInfo(urls, jd) {
      const {code} = await api.addBdInfo({
        apps: this.apps,
        whatsapp: this.whatsapp,
        reason: this.reason,
        howToDo: this.howToDo,
        screenshots: urls,
        jd: jd
      });
      this.showLoading = false;
      if (code === RES_COMMON_CODE.SUCCESS) {
        Toast(this.$t("recruit_bd_submit_success_info"));
        await this.$router.push(`/recruit_bd`);
        return;
      }
      if (code === RES_COMMON_CODE.NO_PERMISSION) {
        Toast(this.$t("common_no_permission"));
        return;
      }

      Toast(this.$t("common_failed_with_errorcode", {code}));
    },
    matchFileType(fileName) {
      let suffix = fileName.substr(fileName.lastIndexOf('.') + 1, fileName.length);
      suffix = suffix.toLowerCase();
      if (suffix === 'pdf' || suffix === 'word') {
        return true;
      }
      return false;
    }
  },
  computed: {

  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include v-layout(bd-info) {
  width: 100%;

  .margin-top-16px {
    margin-top: 16px;
  }

  .margin-bottom-23px {
    margin-bottom: 23px;
  }

  @include edit-view(
    bd-info,
    $width: 100%,
    $height: 98px,
    $font-size: 28px,
    $font-color: $--color-AAAAAA,
    $font-weight: $--font-weight-regular,
    $background-color: $--color-F5F7FA
  ) {
    border-radius: 32px;
    padding: 0px 24px;
    margin: 23px 0 23px
  }

  @include button(
    confirm,
    $width: 638px,
    $height: 96px,
    $text-size: 32px,
    $text-color: $--color-white,
    $border-radius: 48px,
    $background: $--color-00E0D7
  ) {
    margin-top: 40px;
    margin-bottom: 80px;
  }

  .loading-view {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100%;
  }
}
</style>