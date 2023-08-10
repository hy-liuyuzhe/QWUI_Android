<template>
  <div class="upload-file-add-h-layout">
    <upload-file-item-view
      v-for="(file, index) in files"
      :key="index"
      :url="file.url"
      :mediaType="mediaType"
      :class="[index > 0 ? 'margin-start-10px' : '']"
      @on-delete-click="onDeleteClick($event)"
    />
    <input
      class="file-input-view"
      :id="`input-${source}`"
      type="file"
      :accept="inputAccept"
      @change="onFileChange($event)"
    />
    <div
      v-if="showAdd"
      :class="['add-z-layout', isFilesEmpty ? '' : 'margin-start-10px']"
      @click="onAddClick"
    >
      <van-image
        class="add-image-view"
        :src="require('../assets/common_file_add_ic.png')"
      />
    </div>
  </div>
</template>
<script>
import _ from "lodash";
import Vue from "vue";
import { Image as VanImage, Toast } from "vant";
import UploadFileItemView from "@/common/components/UploadFileItemView";
import { log } from "../../../common/bridge/index";
import { MEDIA_TYPE } from "../constant/index";

Vue.use(VanImage);
Vue.use(Toast);

export default {
  name: "UploadFileAddView",
  components: {
    "upload-file-item-view": UploadFileItemView,
  },
  props: {
    maxCount: {
      type: Number,
      default: 3,
    },
    maxSize: {
      type: Number,
      default: 31457280,
    },
    mediaType: {
      type: Number,
      default: MEDIA_TYPE.IMAGE,
    },
    source: String,
    value: [],
  },
  data() {
    return {
      files: [],
    };
  },
  destroyed() {
    this.files.forEach((file) => {
      window.URL.revokeObjectURL(file.url);
    });
  },
  methods: {
    onAddClick() {
      const fileDom = document.querySelector(`#input-${this.source}`);
      fileDom.click();
    },
    onDeleteClick(url) {
      this.files = _.filter(this.files, (file) => file.url !== url);
      window.URL.revokeObjectURL(url);
    },
    onFileChange(e) {
      const file = e.target.files[0];
      if (file.size > this.maxSize) {
        Toast(
          this.$t("common_image_size_limit", {
            MB: (parseFloat(this.maxSize) / (1024 * 1024)).toFixed(2),
          })
        );
        return;
      }
      const url = window.URL.createObjectURL(file);
      this.files.push({ url, file });
    },
  },
  computed: {
    isFilesEmpty() {
      return this.files.length === 0;
    },
    showAdd() {
      return this.files.length < this.maxCount;
    },
    inputAccept() {
      if (this.mediaType === MEDIA_TYPE.VIDEO) {
        return "video/*";
      } else {
        return "image/*";
      }
    },
  },
  watch: {
    files(val) {
      this.$emit(
        "input",
        _.map(val, (file) => file.file)
      );
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include h-layout(upload-file-add) {
  width: 100%;
  .margin-start-10px {
    @include compat.margin-start(10px);
  }
  .file-input-view {
    display: none;
  }
  @include z-layout(add) {
    width: 140px;
    height: 140px;
    background: #f5f7fa;
    border-radius: 16px;
    border: 1px solid #e9e9e9;

    @include image-view(add, 40px, 40px) {
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translateX(-50%) translateY(-50%);
    }
  }
}
</style>
