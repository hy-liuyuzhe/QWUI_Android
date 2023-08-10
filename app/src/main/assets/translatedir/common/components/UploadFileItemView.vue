<template>
  <div class="upload-file-item-z-layout">
    <van-image
      class="content-image-view"
      error-icon="video-o"
      v-if="isVideoType"
      src=""
      radius="8"
    />
    <template v-else>
      <van-image class="content-image-view" radius="8" fit="cover" :src="url" />
    </template>
    <van-image
      class="delete-image-view"
      :src="require('../assets/common_added_file_delete_ic.png')"
      @click="$emit('on-delete-click', url)"
    />
  </div>
</template>
<script>
import Vue from "vue";
import { Image as VanImage, Icon } from "vant";
import { MEDIA_TYPE } from "../constant/index";

Vue.use(VanImage);
Vue.use(Icon);
export default {
  name: "UploadFileItemView",
  props: {
    url: {
      type: String,
      default: "",
    },
    mediaType: {
      type: Number,
      default: MEDIA_TYPE.IMAGE,
    },
  },
  computed: {
    isVideoType() {
      return this.mediaType === MEDIA_TYPE.VIDEO;
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include z-layout(upload-file-item) {
  width: 140px;
  height: 140px;
  border-radius: 16px;
  border: 1px solid #e9e9e9;

  @include image-view(content, 140px, 140px);

  @include image-view(delete, 40px, 40px) {
    position: absolute;
    top: 0;
    right: 0;
  }
}
</style>