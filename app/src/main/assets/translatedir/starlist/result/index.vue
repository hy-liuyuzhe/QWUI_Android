<template>
    <div class="result-root">
        <!-- <van-image :src="resultImage" class="result-img"></van-image> -->
    </div>
</template>
<script>
import Vue from 'vue';
import { Image as VanImage } from 'vant';
import { setImmersionMode } from "../../../common/bridge/index";

Vue.use(VanImage);
export default {
    name: "StarListResult",
    metaInfo: {
        title: "Starlight list",
            meta: [
        {
            name: "viewport",
            content: "width=device-width, initial-scale=1, maximum-scale=1",
        },
    ],
    },
    created() {
        setImmersionMode();
    },
    computed: {
        // resultImage() {
        //     const languageCode = this.$i18n.locale
        //     if (languageCode == "ar") {
        //         return require('../assets/star_list_result_ar.png')
        //     } else {
        //         return require('../assets/star_list_result_en.png')
        //     }
        // },
    }
}
</script>
<style lang="scss" scoped>
.result-root {
    width: 750px;
    height: 2078px;
}
.result-img {
    width: 750px;
    height: 2078px;
}
</style>