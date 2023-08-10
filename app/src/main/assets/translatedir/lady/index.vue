<template>
    <div class="lady-root">
        <div class="lady-top">
            <van-image :src="titleImage" class="title-img"></van-image>
        </div>
        <div class="rights-title-container">
            <span class="in-wyak-text">{{ $t('lady_in_wyak') }}</span>
            <span class="share-right-text">{{ $t('lady_share_rights') }}</span>
        </div>
        <lady-right class="avatar-frame-right" v-bind:title="$t('lady_free_avatar_frame')" >
            <template v-slot:content>
                <frame-right></frame-right>
            </template>
        </lady-right>
        <lady-right class="upgrade-frame-right" v-bind:title="$t('lady_upgrade_right')" >
            <template v-slot:content>
                <upgrade-right></upgrade-right>
            </template>
        </lady-right>
        <lady-right class="report-frame-right" v-bind:title="$t('lady_report_right')" >
            <template v-slot:content>
                <report-right></report-right>
            </template>
        </lady-right>
        <div class="bottom-container">
            <div class="bottom-more">
                <div class="line1"/>
                <span class="more-text">{{ $t('lady_more') }}</span>
                <div class="line2"/>
            </div>
            <span class="more-text-desc">{{ $t('lady_more_1') }}</span>
            <span class="more-text-desc">{{ $t('lady_more_2') }}</span>
        </div>
        
    </div>
</template>
<script>
import Vue from 'vue';
import { Image as VanImage } from 'vant';
import LadyRight from './components/LadyRight';
import FrameRight from './components/FrameRight';
import UpgradeRight from './components/UpgradeRight';
import ReportRight from './components/ReportRight';
import { setImmersionMode } from "../../common/bridge/index";

Vue.use(VanImage);
export default {
    name: "Lady",
    created() {
        setImmersionMode();
    },
    components: {
        "lady-right": LadyRight,
        "frame-right": FrameRight,
        "upgrade-right": UpgradeRight,
        "report-right": ReportRight
    },
    metaInfo: {
        title: "Lady privilege",
            meta: [
        {
            name: "viewport",
            content: "width=device-width, initial-scale=1, maximum-scale=1",
        },
    ],
    },
    computed: {
        titleImage() {
            const languageCode = this.$i18n.locale
            if (languageCode == "ar") {
                return require('./assets/lady_title_ic_ar.png')
            } else if (languageCode == "zh" || languageCode == "zh-Hans") {
                return require('./assets/lady_title_ic_cn.png')
            } else {
                return require('./assets/lady_title_ic_en.png')
            }
        },
    }
}
</script>
<style lang="scss" scoped>
.lady-root {
    width: 750px;
    display: flex;
    flex-direction: column;
    align-items: center;
    background: #A1F8E2;
    background-image: url(('./assets/lady_bottom_bg.png'));
    background-position: bottom;
    background-attachment: f;
    background-size: 100%;
    background-repeat: no-repeat;
}
.lady-top {
    width: 750px;
    height: 840px;
    background-image: url(('./assets/lady_top_bg.png'));
    background-size: 100% 100%;
}
.title-img {
    width: 750px;
    height: 280px;
    margin-top: 60px;
}
.rights-title-container {
    width: 710px;
    height: 228px;
    background-image: url("./assets/lady_small_frame_bg.png");
    background-size: 100% 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: -164px;
}
.in-wyak-text {
    font-size: 48px;
    height: 58px;
    font-family: SFUIText-Semibold, SFUIText;
    font-weight: 600;
    color: #C54D23;
    margin-top: 34px;
}
.share-right-text {
    width: 630px;
    font-size: 32px;
    font-family: SFUIText-Regular, SFUIText;
    font-weight: 400;
    color: #C54D23;
    margin-top: 16px;
    text-align: start;
    margin-left: 40px;
}
.avatar-frame-right {
    margin-top: 16px;
    z-index: 1;
}
.upgrade-frame-right {
    margin-top: 20px;
    z-index: 1;
}
.report-frame-right {
    margin-top: 20px;
}
.bottom-container {
    width: 750px;
    margin-top: 40px;
    display: flex;
    flex-direction: column;
    align-items: center;
    padding-bottom: 60px;
}
.bottom-more {
    display: flex;
    flex-direction: row;
    align-items: center;
}
.line1 {
    width: 80px;
    height: 2px;
    background-color: white;
    display: inline;
}
.more-text {
    font-size: 36px;
    font-family: SFUIText-Semibold, SFUIText;
    font-weight: 600;
    color: #FFFFFF;
    margin-left: 16px;
}
.line2 {
    width: 80px;
    height: 2px;
    background-color: white;
    margin-left: 16px;
    display: inline;
}
.more-text-desc {
    width: 670px;
    font-size: 26px;
    font-family: SFUIText-Regular, SFUIText;
    font-weight: 400;
    color: #FFFFFF;
    line-height: 31px;
    margin-top: 20px;
}
</style>