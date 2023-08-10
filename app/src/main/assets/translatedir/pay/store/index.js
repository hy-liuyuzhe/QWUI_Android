import api from "../api/index";
import { RES_COMMON_CODE } from "../../../common/network/constant";
import { CHANNEL, ORDER_PRODUCT_TYPE } from "../constant/constant";

export const fillChannels = (channelList, countryCode) => {
    if (channelList && channelList.length > 0) {
        channelList.forEach((channelInfo) => {
            channelInfo.channelIcon = getChannelIcon(channelInfo.type)
            channelInfo.products = []
            channelInfo.countryCode = countryCode
        })
    }
};

const getChannelIcon = (type) => {
    if (type == CHANNEL.CHANNEL_MERCHANT) {
        return require('../assets/pay_channel_merchant.png')
    } else if (type == CHANNEL.CHANNEL_GOOGLE) {
        return require('../assets/pay_channel_google.png');
    } else if (type == CHANNEL.CHANNEL_VISA) {
        return require('../assets/pay_channel_visa.png');
    } else if (type == CHANNEL.CHANNEL_FAWRY) {
        return require('../assets/pay_channel_fawry.png');
    } else if (type == CHANNEL.CHANNEL_BENEFIT) {
        return require('../assets/pay_channel_benefit.png');
    } else if (type == CHANNEL.CHANNEL_KNET) {
        return require('../assets/pay_channel_knet.png');
    } else if (type == CHANNEL.CHANNEL_MADA) {
        return require('../assets/pay_channel_mada.png');
    } else if (type == CHANNEL.CHANNEL_TROY) {
        return require('../assets/pay_channel_tory.png');
    } else if (type == CHANNEL.CHANNEL_BANK_TRANSFER) {
        return require('../assets/pay_channel_bank_transfer.png');
    } else if (type == CHANNEL.CHANNEL_HUAWEI) {
        return require('../assets/pay_channel_huawei.png');
    } else if (type == CHANNEL.CHANNEL_WALLET) {
        return require('../assets/pay_channel_wallet.png');
    } else if (type == CHANNEL.CHANNEL_STCPAY) {
        return require('../assets/pay_channel_stcpay.png');
    } else {
        return ""
    }
}

export default {
    namespaced: true,
    state: () => ({
        channels: [], //充值渠道列表
        merchantShipOpen: false//币商开关是否打开
    }),
    mutations: {
        refreshChannelList(state, payload) {
            fillChannels(payload.list, payload.countryCode);
            state.channels = payload.list;
        },
        refreshProductList(state, payload) {
            state.channels.forEach((channelInfo) => {
                if (channelInfo.type == payload.type) {
                    var coinProduct = [];
                    if (payload.list && payload.list.length > 0) {
                        payload.list.forEach(productInfo => {
                            if (productInfo.productType && productInfo.productType == ORDER_PRODUCT_TYPE.TYPE_COIN) {
                                //过滤productType
                                coinProduct.push(productInfo);
                            }
                        });
                    }
                    channelInfo.products = coinProduct;
                }
            })
        },
        refreshMerchantShipOpen(state, payload) {
            state.merchantShipOpen = payload;
        }
    },
    actions: {
        async requestChannelList({ state, commit, dispatch }, payload) {
            let channels = [];
            if (state.merchantShipOpen) {
                channels.push({
                    type: CHANNEL.CHANNEL_MERCHANT,
                })
            } else {
                const merchantRes = await api.getMerchantConfig(payload);
                if (merchantRes.code === RES_COMMON_CODE.SUCCESS) {
                    commit("refreshMerchantShipOpen", true);
                    channels.push({
                        type: CHANNEL.CHANNEL_MERCHANT,
                    })
                } else {
                    commit("refreshMerchantShipOpen", false);
                }
            }
            const res = await api.getChannels(payload);
            const { code, data } = res;
            if (code === RES_COMMON_CODE.SUCCESS) {
                channels.push(...data);
            }
            commit("refreshChannelList", { list: channels, countryCode: payload.countryCode });
            if (data && data.length > 0) {
                data.forEach((channelInfo) => {
                    if (channelInfo.type != CHANNEL.CHANNEL_GOOGLE || channelInfo.type != CHANNEL.CHANNEL_HUAWEI) {
                        dispatch("requestProductList", { countryCode: payload.countryCode, type: channelInfo.type });
                    }
                })
            }
        },
        async requestProductListIfNeed({ dispatch, state }, payload) {
            state.channels.forEach((channelInfo) => {
                if (channelInfo.type == payload.type && (!channelInfo.products || channelInfo.products.length == 0)) {
                    dispatch("requestProductList", { countryCode: payload.countryCode, type: channelInfo.type });
                }
            })
        },
        async requestProductList({ commit }, payload) {
            const res = await api.getProducts(payload);
            const { code, data } = res;
            if (code == RES_COMMON_CODE.SUCCESS) {
                commit("refreshProductList", { list: data, type: payload.type });
            }
        },
    },
    getters: {},
};