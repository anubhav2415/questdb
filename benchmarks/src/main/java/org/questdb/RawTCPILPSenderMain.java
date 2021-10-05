/*******************************************************************************
 *     ___                  _   ____  ____
 *    / _ \ _   _  ___  ___| |_|  _ \| __ )
 *   | | | | | | |/ _ \/ __| __| | | |  _ \
 *   | |_| | |_| |  __/\__ \ |_| |_| | |_) |
 *    \__\_\\__,_|\___||___/\__|____/|____/
 *
 *  Copyright (c) 2014-2019 Appsicle
 *  Copyright (c) 2019-2020 QuestDB
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 ******************************************************************************/

package org.questdb;

import io.questdb.network.Net;
import io.questdb.std.Chars;
import io.questdb.std.MemoryTag;
import io.questdb.std.Os;
import io.questdb.std.Unsafe;

public class RawTCPILPSenderMain {
    public static void main(String[] args) {
        final String ilp = "bybit_perpetual_btcusdt_ob_features,symbol=BTCUSDT,side=Buy,time_iso8601=2021-09-27_12:04:19.100000,trade_id=7d4676b9-08ae-5659-982c-629bd3a99f75,ask_ob=00000000000000000400000002000000fffffffff00500001000000000000a000c000600050008000a000000000104000c00000008000800000004000800000004000000010000000400000080faffff0000010e180000002000000004000000020000007805000028000000040000006c6973740000000014fbffff0000010004000000020000000000000001000000c4faffff0000010c14000000180000000400000001000000100000000200000031310000b8faffffecfaffff0000010d180000002000000004000000020000006804000014000000040000006974656d00000000e8faffff1cfbffff0000010e1c00000024000000040000000300000014040000540000002c0000000400000076616c7300000000b4fbffff00000100040000000300000000000000010000000200000068fbffff0000010510000000140000000400000000000000010000003500000058fbffff8cfbffff0000010c1400000018000000040000000100000010000000020000003131000080fbffffb4fbffff0000010d18000000200000000400000002000000f002000014000000040000006974656d00000000b0fbffffe4fbffff0000010e180000002000000004000000020000009c020000280000000400000076616c730000000078fcffff000001000400000002000000000000000100000028fcffff0000010c140000001800000004000000010000001000000002000000313000001cfcffff50fcffff0000010e180000002000000004000000020000000c02000028000000040000006974656d00000000e4fcffff000001000400000002000000000000000100000094fcffff0000010c1400000018000000040000000100000010000000020000003131000088fcffffbcfcffff0000010d180000002000000004000000020000001401000014000000040000006974656d00000000b8fcffffecfcffff0000010e20000000280000000400000004000000c00000008800000058000000300000000400000076616c730000000088fdffff0000010004000000040000000000000001000000020000000300000040fdffff0000010510000000140000000400000000000000010000003500000030fdffff64fdffff00000102100000001400000004000000000000000200000031360000d4ffffff000000012000000090fdffff00000102100000001c0000000400000000000000020000003135000008000c0008000700080000000000000120000000c4fdffff00000101100000001400000004000000000000000100000030000000b4fdffffe8fdffff0000010e180000002000000004000000020000005000000028000000040000006b657973000000007cfeffff00000100040000000200000000000000010000002cfeffff000001051000000014000000040000000000000001000000350000001cfeffff50feffff0000010110000000140000000400000000000000010000003000000040feffff74feffff0000010110000000140000000400000000000000010000003000000064feffff98feffff0000010110000000140000000400000000000000010000003000000088feffffbcfeffff0000010e180000002000000004000000020000005000000028000000040000006b6579730000000050ffffff000001000400000002000000000000000100000000ffffff00000105100000001400000004000000000000000100000035000000f0feffff24ffffff0000010110000000140000000400000000000000010000003000000014ffffff48ffffff0000010110000000140000000400000000000000010000003000000038ffffff6cffffff0000010e180000002800000004000000020000005800000030000000040000006b6579730000000008000c0006000800080000000000010004000000020000000000000001000000b8ffffff00000105100000001400000004000000000000000100000035000000a8ffffffdcffffff00000101100000001400000004000000000000000100000030000000ccffffff100014000800060007000c00000010001000000000000101100000001800000004000000000000000100000030000000040004000400000000000000ffffffff1805000014000000000000000c0016000600050008000c000c0000000003040018000000e00100000000000000000a0018000c00040008000a000000fc020000100000000100000000000000000000002e000000000000000000000001000000000000000800000000000000040000000000000010000000000000000000000000000000100000000000000008000000000000001800000000000000000000000000000018000000000000000200000000000000200000000000000008000000000000002800000000000000000000000000000028000000000000000c0000000000000038000000000000000c00000000000000480000000000000002000000000000005000000000000000080000000000000058000000000000000000000000000000580000000000000008000000000000006000000000000000000000000000000060000000000000000200000000000000680000000000000008000000000000007000000000000000000000000000000070000000000000000c0000000000000080000000000000000a000000000000009000000000000000020000000000000098000000000000000800000000000000a0000000000000000000000000000000a0000000000000000c00000000000000b0000000000000000400000000000000b8000000000000001000000000000000c8000000000000000000000000000000c8000000000000001400000000000000e0000000000000000000000000000000e0000000000000000800000000000000e800000000000000200000000000000008010000000000000000000000000000080100000000000024000000000000003001000000000000340000000000000068010000000000000800000000000000700100000000000020000000000000009001000000000000000000000000000090010000000000001000000000000000a0010000000000000000000000000000a0010000000000000800000000000000a8010000000000000000000000000000a8010000000000000c00000000000000b8010000000000001000000000000000c8010000000000000000000000000000c8010000000000000800000000000000d0010000000000000c00000000000000000000001e0000000100000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000200000000000000000000000000000002000000000000000000000000000000000000000000000000000000000000000200000000000000000000000000000002000000000000000000000000000000000000000000000000000000000000000100000000000000000000000000000002000000000000000000000000000000020000000000000000000000000000000000000000000000000000000000000002000000000000000000000000000000020000000000000000000000000000000000000000000000000000000000000002000000000000000000000000000000040000000000000000000000000000000000000000000000000000000000000004000000000000000000000000000000080000000000000000000000000000000800000000000000000000000000000000000000000000000000000000000000080000000000000000000000000000000800000000000000000000000000000000000000000000000000000000000000040000000000000000000000000000000200000000000000000000000000000002000000000000000000000000000000010000000000000000000000000000000100000000000000000000000000000000000000020000000101000000000000000000000100000000000000040000000c00000000000000646174615f7079747970655f000000000102000000000000000000000000000000000000020000000101000000000000000000000100000000000000060000000a00000000000000626c6f636b736178657300000000000001010000000000000000000001000000000000000200000004000000000000000101010100000000000000000100000002000000030000000000000002000000040000000600000008000000000000000101010101010101000000000100000002000000030000000400000005000000060000000700000000000000090000000e000000170000001c00000020000000280000002c0000003400000000000000706c6163656d656e74626c6f636b706c6163656d656e74626c6f636b646174615f7079747970655f646174615f7079747970655f00000000010101010203020300000000010000000200000003000000000000000000000001000000010000000000000001000000020000000300000000000000010000000000000008000000100000000000000070642e496e64657870642e496e646578000000000c00000070642e446174614672616d6500000000ffffffff00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000ffffffffb800000014000000000000000c001a000600050008000c000c000000000404002000000008000000000000000000000000000e002800070008000c00100014000e000000000000026000000028000000180000000000000000000000080000000000000000000000010000000800000000000000010000000c00000008001400080004000800000010000000010000000000000000000000000000000000000008000c0008000700080000000000000140000000000000000000000001000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000fffffffff800000014000000000000000c001a000600050008000c000c000000000404002000000040060000000000000000000000000e002800070008000c00100014000e000000000000038c0000003400000018000000000000000000000040060000000000000000000002000000080000000000000008000000000000000000000002000000300000000c0000000800100008000400080000000c000000c80000000000000000000000000000000800140008000400080000001000000001000000000000000000000000000000000006000800060006000000000002000000000000000000000000000000000000000000000000000000000000000000fa7e6abc7493883ffca9f1d24d62503f7b14ae47e17aa43f9a9999999999d93f9a9999999999c93fc3f5285c8fc2ed3f3bdf4f8d976e823f79e9263108ac7c3fec51b81e85ebc13ffca9f1d24d62503ffca9f1d24d62503ffca9f1d24d62503ffca9f1d24d62503ffca9f1d24d62603f7b14ae47e17a843ffa7e6abc7493683ffca9f1d24d62503ffca9f1d24d62503ffca9f1d24d62603f39b4c876be9f9a3ffca9f1d24d62503ffca9f1d24d62503ffca9f1d24d62603ffca9f1d24d62603f9a9999999999a93f6abc74931804f03ffca9f1d24d62603ffca9f1d24d62503ffca9f1d24d62503ffa7e6abc7493683ffca9f1d24d62603ffa7e6abc7493783f79e9263108acac3f0ad7a3703d0ab73ffca9f1d24d62503ffca9f1d24d62503ffca9f1d24d62603f48e17a14ae0720405a643bdf4f8da73ffa7e6abc7493883ffca9f1d24d62603f3bdf4f8d976e823ffca9f1d24d62503ffa7e6abc7493683ffca9f1d24d62603ffca9f1d24d62503ffca9f1d24d62603ffca9f1d24d62503ffca9f1d24d62503fb81e85eb51b88e3ffca9f1d24d62503f9a9999999999a93ffca9f1d24d62603ffca9f1d24d62503ffa7e6abc74131140fca9f1d24d62503ffa7e6abc7493683ffca9f1d24d62503f4260e5d022dbb93f1904560e2db2ad3f83c0caa145b61040fa7e6abc7493783ffa7e6abc7493883f79e9263108ac8c3ffca9f1d24d62503ffca9f1d24d62503ffca9f1d24d62703ffca9f1d24d62503f7b14ae47e17a843ffca9f1d24d62503ffa7e6abc7493683ffca9f1d24d62503f39b4c876be9fba3f9a9999999999a93f39b4c876be9f8a3ffca9f1d24d62503ffca9f1d24d62503fba490c022b87863f62105839b4c80c40fca9f1d24d62503ffca9f1d24d62503ffa7e6abc7493683fee7c3f355ebae13ffca9f1d24d62503ffca9f1d24d62503ffca9f1d24d62503fbe9f1a2fdd24d63f39b4c876be9f9a3f9a9999999999a93fd9cef753e3a5bb3f7b14ae47e17a843f9cc420b07268913ffca9f1d24d62603ffca9f1d24d62503ffa7e6abc7493683f2b8716d9cef7a33f3bdf4f8d976eb23f4260e5d0220b4040fca9f1d24d62503ffca9f1d24d62503ff0a7c64b3789c13ffca9f1d24d62503f9a9999999999993fb81e85eb51b8be3f77be9f1a2fdd11407b14ae47e17ac43f39b4c876be9faa3ffca9f1d24d62503fc3f5285c8fc20d40ba490c022b87963ffca9f1d24d62503ffca9f1d24d62603ffca9f1d24d62503ffa7e6abc7493683f986e1283c0caed3ffca9f1d24d62503ffca9f1d24d62503f8b6ce7fba9f1a23fc976be9f1a2fbd3ffca9f1d24d62503ffa7e6abc7493783ffca9f1d24d62603f4260e5d022dbd13fdd2406819543eb3fb81e85eb51b89e3ffca9f1d24d62503ffa7e6abc7493683ffca9f1d24d62503fdbf97e6abc74e33ffca9f1d24d62503ffca9f1d24d62503f46b6f3fdd4780940fca9f1d24d62503fcdcccccccccc1140fca9f1d24d62703ffca9f1d24d62603ffca9f1d24d62503ffa7e6abc7493683fac1c5a643bdfe33fb81e85eb51b89e3f9a9999999999b93ffa7e6abc7493683f7f6abc749318f23f08ac1c5a643bbf3f295c8fc2f5281440b6f3fdd478e9e23ffca9f1d24d62603ffca9f1d24d62503f9eefa7c64b37d93ffca9f1d24d62503fc74b37894160fd3fa245b6f3fdd4b83fba490c022b87863f5a643bdf4f8d973ffca9f1d24d62503ffca9f1d24d62503ff6285c8fc2f5e83f3bdf4f8d976ef43fba490c022b87963f105839b4c876d63ffa7e6abc7493683fd9cef753e3a5f53f39b4c876be9fd23fba490c022b87863ffca9f1d24d62c03f2db29defa7c6f93f8fc2f5285c8ff03f3bdf4f8d976ef83f54e3a59bc4200b40f853e3a59bc40540e5d022dbf97e13403bdf4f8d976ef83ffca9f1d24d62503fd9cef753e3a5e73f75931804560ed53fec51b81e85ebe93f3d0ad7a3703dd23f79e9263108acac3fb4c876be9f1ad73f6de7fba9f1d2cd3f000000000000e83fb81e85eb51b89e3ff6285c8fc2f5c83ffca9f1d24d62503f7b14ae47e17a943f7b14ae47e17a943fe7fba9f1d24dd23fcdcccccccccce43f08ac1c5a643be33f39b4c876be9fe23f1f85eb51b81edd3f5839b4c876bee33fb81e85eb51b8d63f643bdf4f8d171040333333333333d33ffca9f1d24d62503f000000000000104000000000000010400000000000000840f853e3a59b441140ffffffffb800000014000000000000000c001a000600050008000c000c000000000404002000000008000000000000000000000000000e002800070008000c00100014000e000000000000026000000028000000180000000000000000000000080000000000000000000000010000000800000000000000010000000c00000008001400080004000800000010000000010000000000000000000000000000000000000008000c0008000700080000000000000140000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000fffffffff800000014000000000000000c001a000600050008000c000c000000000404002000000040060000000000000000000000000e002800070008000c00100014000e000000000000038c0000003400000018000000000000000000000040060000000000000000000002000000400600000000000008000000000000000000000002000000300000000c0000000800100008000400080000000c000000c80000000000000000000000000000000800140008000400080000001000000001000000000000000000000000000000000006000800060006000000000002000000000000000000000000000000000000000000000000000000000000000000000000000061e54000000000e060e540000000009060e540000000005060e540000000004060e540000000003060e540000000002060e540000000001060e54000000000805fe54000000000005fe54000000000e05ee54000000000d05ee54000000000a05ee54000000000805ee54000000000005ee54000000000a05de54000000000905de54000000000805de54000000000e05ce54000000000c05ce54000000000805ce54000000000505ce54000000000405ce54000000000205ce54000000000105ce54000000000805be54000000000605be54000000000405be54000000000105be54000000000005be54000000000e05ae54000000000b05ae54000000000a05ae54000000000805ae54000000000205ae54000000000005ae54000000000f059e54000000000e059e54000000000d059e54000000000c059e540000000004059e540000000000059e540000000009058e540000000008058e540000000007058e54000000000e057e54000000000b057e540000000008057e540000000005057e540000000002057e540000000000057e54000000000e056e54000000000d056e54000000000c056e540000000008056e540000000001056e540000000000056e54000000000d055e54000000000b055e540000000009055e540000000005055e540000000004055e540000000002055e54000000000f054e54000000000e054e54000000000d054e54000000000c054e540000000008054e540000000000054e540000000009053e540000000008053e540000000006053e540000000005053e540000000002053e54000000000b052e540000000006052e540000000005052e54000000000c051e540000000008051e540000000002051e540000000001051e54000000000e050e540000000004050e540000000000050e54000000000e04fe54000000000d04fe54000000000c04fe54000000000804fe54000000000604fe54000000000104fe54000000000004fe54000000000c04ee54000000000b04ee54000000000904ee54000000000604ee54000000000204ee54000000000f04de54000000000e04de54000000000c04de54000000000a04de54000000000704de54000000000604de54000000000504de54000000000304de54000000000204de54000000000f04ce54000000000c04ce54000000000a04ce54000000000904ce54000000000804ce54000000000104ce54000000000004ce54000000000f04be54000000000e04be54000000000f04ae54000000000e04ae54000000000d04ae54000000000c04ae54000000000a04ae54000000000804ae54000000000404ae54000000000304ae54000000000104ae54000000000004ae54000000000a049e540000000009049e540000000006049e540000000004049e540000000007048e540000000006048e540000000005048e540000000003048e540000000002048e540000000008047e540000000007047e540000000002047e540000000001047e54000000000f046e54000000000e046e54000000000c046e54000000000a046e540000000006046e540000000005046e540000000004046e540000000001046e540000000000046e54000000000e045e54000000000d045e54000000000a045e540000000008045e540000000004045e540000000001045e540000000000045e54000000000a044e540000000009044e540000000007044e540000000004044e540000000001044e54000000000d043e540000000008043e540000000007043e540000000005043e540000000004043e540000000000043e54000000000f042e54000000000c042e54000000000a042e540000000009042e540000000008042e540000000007042e540000000006042e540000000005042e540000000001042e54000000000f041e54000000000e041e54000000000c041e54000000000b041e540000000008041e540000000004041e540000000003041e540000000002041e540000000000041e54000000000e040e54000000000d040e54000000000c040e54000000000b040e540000000008040e540000000007040e540000000004040e540000000003040e540000000002040e540000000001040e540000000000040e54000000000d03fe54000000000c03fe54000000000903fe54000000000003fe54000000000f03ee54000000000e03ee54000000000d03ee540f200000000000000800595e7000000000000008c1870616e6461732e636f72652e696e64657865732e62617365948c0a5f6e65775f496e64657894939468008c05496e6465789493947d94288c0464617461948c156e756d70792e636f72652e6d756c74696172726179948c0c5f7265636f6e7374727563749493948c056e756d7079948c076e6461727261799493944b0085944301629487945294284b014b028594680a8c0564747970659493948c024f3894898887945294284b038c017c944e4e4e4affffffff4affffffff4b3f749462895d94288c057072696365948c06416d6f756e7494657494628c046e616d65944e75869452942e8d0000000000000080059582000000000000008c1870616e6461732e636f72652e696e64657865732e62617365948c0a5f6e65775f496e6465789493948c1970616e6461732e636f72652e696e64657865732e72616e6765948c0a52616e6765496e6465789493947d94288c046e616d65944e8c057374617274944b008c0473746f70944bc88c0473746570944b0175869452942e,bid_ob=00000000000000000400000002000000fffffffff00500001000000000000a000c000600050008000a000000000104000c00000008000800000004000800000004000000010000000400000080faffff0000010e180000002000000004000000020000007805000028000000040000006c6973740000000014fbffff0000010004000000020000000000000001000000c4faffff0000010c14000000180000000400000001000000100000000200000031310000b8faffffecfaffff0000010d180000002000000004000000020000006804000014000000040000006974656d00000000e8faffff1cfbffff0000010e1c00000024000000040000000300000014040000540000002c0000000400000076616c7300000000b4fbffff00000100040000000300000000000000010000000200000068fbffff0000010510000000140000000400000000000000010000003500000058fbffff8cfbffff0000010c1400000018000000040000000100000010000000020000003131000080fbffffb4fbffff0000010d18000000200000000400000002000000f002000014000000040000006974656d00000000b0fbffffe4fbffff0000010e180000002000000004000000020000009c020000280000000400000076616c730000000078fcffff000001000400000002000000000000000100000028fcffff0000010c140000001800000004000000010000001000000002000000313000001cfcffff50fcffff0000010e180000002000000004000000020000000c02000028000000040000006974656d00000000e4fcffff000001000400000002000000000000000100000094fcffff0000010c1400000018000000040000000100000010000000020000003131000088fcffffbcfcffff0000010d180000002000000004000000020000001401000014000000040000006974656d00000000b8fcffffecfcffff0000010e20000000280000000400000004000000c00000008800000058000000300000000400000076616c730000000088fdffff0000010004000000040000000000000001000000020000000300000040fdffff0000010510000000140000000400000000000000010000003500000030fdffff64fdffff00000102100000001400000004000000000000000200000031360000d4ffffff000000012000000090fdffff00000102100000001c0000000400000000000000020000003135000008000c0008000700080000000000000120000000c4fdffff00000101100000001400000004000000000000000100000030000000b4fdffffe8fdffff0000010e180000002000000004000000020000005000000028000000040000006b657973000000007cfeffff00000100040000000200000000000000010000002cfeffff000001051000000014000000040000000000000001000000350000001cfeffff50feffff0000010110000000140000000400000000000000010000003000000040feffff74feffff0000010110000000140000000400000000000000010000003000000064feffff98feffff0000010110000000140000000400000000000000010000003000000088feffffbcfeffff0000010e180000002000000004000000020000005000000028000000040000006b6579730000000050ffffff000001000400000002000000000000000100000000ffffff00000105100000001400000004000000000000000100000035000000f0feffff24ffffff0000010110000000140000000400000000000000010000003000000014ffffff48ffffff0000010110000000140000000400000000000000010000003000000038ffffff6cffffff0000010e180000002800000004000000020000005800000030000000040000006b6579730000000008000c0006000800080000000000010004000000020000000000000001000000b8ffffff00000105100000001400000004000000000000000100000035000000a8ffffffdcffffff00000101100000001400000004000000000000000100000030000000ccffffff100014000800060007000c00000010001000000000000101100000001800000004000000000000000100000030000000040004000400000000000000ffffffff1805000014000000000000000c0016000600050008000c000c0000000003040018000000e00100000000000000000a0018000c00040008000a000000fc020000100000000100000000000000000000002e000000000000000000000001000000000000000800000000000000040000000000000010000000000000000000000000000000100000000000000008000000000000001800000000000000000000000000000018000000000000000200000000000000200000000000000008000000000000002800000000000000000000000000000028000000000000000c0000000000000038000000000000000c00000000000000480000000000000002000000000000005000000000000000080000000000000058000000000000000000000000000000580000000000000008000000000000006000000000000000000000000000000060000000000000000200000000000000680000000000000008000000000000007000000000000000000000000000000070000000000000000c0000000000000080000000000000000a000000000000009000000000000000020000000000000098000000000000000800000000000000a0000000000000000000000000000000a0000000000000000c00000000000000b0000000000000000400000000000000b8000000000000001000000000000000c8000000000000000000000000000000c8000000000000001400000000000000e0000000000000000000000000000000e0000000000000000800000000000000e800000000000000200000000000000008010000000000000000000000000000080100000000000024000000000000003001000000000000340000000000000068010000000000000800000000000000700100000000000020000000000000009001000000000000000000000000000090010000000000001000000000000000a0010000000000000000000000000000a0010000000000000800000000000000a8010000000000000000000000000000a8010000000000000c00000000000000b8010000000000001000000000000000c8010000000000000000000000000000c8010000000000000800000000000000d0010000000000000c00000000000000000000001e0000000100000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000200000000000000000000000000000002000000000000000000000000000000000000000000000000000000000000000200000000000000000000000000000002000000000000000000000000000000000000000000000000000000000000000100000000000000000000000000000002000000000000000000000000000000020000000000000000000000000000000000000000000000000000000000000002000000000000000000000000000000020000000000000000000000000000000000000000000000000000000000000002000000000000000000000000000000040000000000000000000000000000000000000000000000000000000000000004000000000000000000000000000000080000000000000000000000000000000800000000000000000000000000000000000000000000000000000000000000080000000000000000000000000000000800000000000000000000000000000000000000000000000000000000000000040000000000000000000000000000000200000000000000000000000000000002000000000000000000000000000000010000000000000000000000000000000100000000000000000000000000000000000000020000000101000000000000000000000100000000000000040000000c00000000000000646174615f7079747970655f000000000102000000000000000000000000000000000000020000000101000000000000000000000100000000000000060000000a00000000000000626c6f636b736178657300000000000001010000000000000000000001000000000000000200000004000000000000000101010100000000000000000100000002000000030000000000000002000000040000000600000008000000000000000101010101010101000000000100000002000000030000000400000005000000060000000700000000000000090000000e000000170000001c00000020000000280000002c0000003400000000000000706c6163656d656e74626c6f636b706c6163656d656e74626c6f636b646174615f7079747970655f646174615f7079747970655f00000000010101010203020300000000010000000200000003000000000000000000000001000000010000000000000001000000020000000300000000000000010000000000000008000000100000000000000070642e496e64657870642e496e646578000000000c00000070642e446174614672616d6500000000ffffffff00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000ffffffffb800000014000000000000000c001a000600050008000c000c000000000404002000000008000000000000000000000000000e002800070008000c00100014000e000000000000026000000028000000180000000000000000000000080000000000000000000000010000000800000000000000010000000c00000008001400080004000800000010000000010000000000000000000000000000000000000008000c0008000700080000000000000140000000000000000000000001000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000fffffffff800000014000000000000000c001a000600050008000c000c000000000404002000000040060000000000000000000000000e002800070008000c00100014000e000000000000038c0000003400000018000000000000000000000040060000000000000000000002000000080000000000000008000000000000000000000002000000300000000c0000000800100008000400080000000c000000c80000000000000000000000000000000800140008000400080000001000000001000000000000000000000000000000000006000800060006000000000002000000000000000000000000000000000000000000000000000000000000000000e9263108acfc4e400000000000001040713d0ad7a370cd3f39b4c876be9fba3f7f6abc749318c43f52b81e85eb51d03f7f6abc749318c43f08ac1c5a643bbf3f9cc420b07268b13f9a9999999999c93ff0a7c64b3789f93f068195438b6cfd3fc976be9f1a2fbd3f91ed7c3f351e3040fed478e926310a407d3f355eba490340e9263108ac1cea3fd122dbf97e6aec3f1904560e2db2cd3fa8c64b3789410240508d976e1283d03fd9cef753e3a5d33f7b14ae47e17a843f7b14ae47e17a843f75931804560ee93f39b4c876be9f9a3f9a9999999999d93ffa7e6abc7493a83f6abc74931804c63ffca9f1d24d62803f1904560e2db2f13f79e9263108ac7c3fcdcccccccccce43f986e1283c0caed3f2fdd24068195fd3fe17a14ae47e10740f6285c8fc2f5c83f273108ac1c5a08402b8716d9cef7f13fb81e85eb51b8ae3fe17a14ae47e1ba3f295c8fc2f528bc3ffca9f1d24d62503f736891ed7c3fb53ffed478e92631d03f5a643bdf4f8de33f2b8716d9cef7c33f333333333333b33fee7c3f355ebac93fdf4f8d976e12c33ffca9f1d24d62503fba490c022b87863fc1caa145b6f3bd3ffca9f1d24d62503f1283c0caa145e23fd122dbf97e6abc3f9a9999999999a93ffa7e6abc7493c83fdbf97e6abc74933fdbf97e6abcf410402b8716d9cef7a33f8b6ce7fba9f1d23f52b81e85eb5138404260e5d022dbd13fdf4f8d976e12d33f39b4c876be9f8a3fac1c5a643bdfcf3f7f6abc749318e03ffca9f1d24d62503f91ed7c3f355eba3ffca9f1d24d62503ffca9f1d24d62703fd34d62105839e03fe5d022dbf97eea3fc976be9f1a2fdd3fc520b072689100404260e5d022db014004560e2db21d114079e9263108ac7c3f4c37894160e5a03ffa7e6abc7493983f7b14ae47e17a943f9a9999999999a93ffa7e6abc7493b83f8d976e1283c0ca3fc3f5285c8fc2ed3fe9263108ac1caa3fb29defa7c64bc73f3bdf4f8d976eb23f3bdf4f8d97ee3d40cba145b6f3fda43f9a9999999999a93fb81e85eb51b88e3f4a0c022b8716a93f79e9263108accc3f4c37894160e5d83f4a0c022b8716b93ffca9f1d24d62803f39b4c876be9f9a3ffca9f1d24d62603ffca9f1d24d62703f1904560e2db2dd3f3bdf4f8d976e923ffca9f1d24d62503f7f6abc749318c43f9a9999999999b93ffca9f1d24d62603f508d976e1283f63f1d5a643bdf4fdd3f7b14ae47e17a743f333333333333b33fee7c3f355ebac93fcff753e3a59bc43fc3f5285c8fc2b53f000000000000f03fb81e85eb51b8d63fb29defa7c6cb1940000000000000e03f9a9999999999b93fdd2406819543d33f2db29defa7c60340736891ed7c3fb53ffca9f1d24d62503fe7fba9f1d24dc23f8195438b6ce7bb3ffed478e926f14040f853e3a59bc41b40643bdf4f8d972c407b14ae47e17a943fb81e85eb51b88e3f6abc74931804b63f9a9999999999c93fc3f5285c8fc2e93ffa7e6abc7493683f9eefa7c64b37d13f62105839b4c8c63fc976be9f1a2fcd3fb81e85eb51b89e3fba490c022b87863f3bdf4f8d976e923f39b4c876be9faa3ffca9f1d24d62503f333333333333c33f7b14ae47e17a743f333333333333d33f2fdd24068195d33f4a0c022b8716fb3f7d3f355eba49e83fba490c022b87863f931804560e2de23f5a643bdf4f8d973f333333333333d33f3bdf4f8d976e823ffca9f1d24d62903f7b14ae47e17a843f1b2fdd240681b53f39b4c876be9f9a3f9a9999999999a93f894160e5d022ab3f1b2fdd240681084079e9263108ac9c3ffa7e6abc7493783f79e9263108ac8c3f60e5d022dbf9ce3fe3a59bc420b0b23ffca9f1d24d62a03f7b14ae47e17a743f54e3a59bc420b03ffca9f1d24d62803ffca9f1d24d62703f54e3a59bc420d03f75931804560ecd3fec51b81e85ebc13ffa7e6abc74931d406abc74931804f23ffca9f1d24d62503f7b14ae47e17a843ffca9f1d24d62503f3bdf4f8d976ea23f21b0726891edbc3f8195438b6ce7d33ffca9f1d24d62503f333333333333b33fdf4f8d976e921640fca9f1d24d62503f4c37894160e5a03f7b14ae47e17a843f333333333333d33f7b14ae47e17a843f9cc420b07268a13f39b4c876be9faa3ffa7e6abc7493783f9cc420b07268b13f48e17a14ae47c13f08ac1c5a643bd73ffa7e6abc7493783f7d3f355eba49cc3f6abc74931804b63f7b14ae47e17a843fa69bc420b072c83fffffffffb800000014000000000000000c001a000600050008000c000c000000000404002000000008000000000000000000000000000e002800070008000c00100014000e000000000000026000000028000000180000000000000000000000080000000000000000000000010000000800000000000000010000000c00000008001400080004000800000010000000010000000000000000000000000000000000000008000c0008000700080000000000000140000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000fffffffff800000014000000000000000c001a000600050008000c000c000000000404002000000040060000000000000000000000000e002800070008000c00100014000e000000000000038c0000003400000018000000000000000000000040060000000000000000000002000000400600000000000008000000000000000000000002000000300000000c0000000800100008000400080000000c000000c8000000000000000000000000000000080014000800040008000000100000000100000000000000000000000000000000000600080006000600000000000200000000000000000000000000000000000000000000000000000000000000000000000000c03ee54000000000b03ee54000000000203ee54000000000f03de54000000000e03de54000000000a03de54000000000603de54000000000403de54000000000303de54000000000103de54000000000f03ce54000000000a03ce54000000000903ce54000000000603be54000000000503be54000000000403be54000000000303be54000000000203be54000000000103be54000000000003be54000000000f03ae54000000000e03ae54000000000d03ae54000000000b03ae54000000000903ae54000000000703ae54000000000603ae54000000000403ae54000000000303ae54000000000203ae54000000000103ae54000000000003ae54000000000f039e54000000000e039e54000000000d039e54000000000c039e54000000000b039e54000000000a039e540000000009039e540000000007039e540000000005039e540000000004039e540000000002039e540000000000039e54000000000e038e54000000000d038e54000000000c038e54000000000a038e540000000008038e540000000007038e540000000006038e540000000004038e540000000003038e540000000001038e540000000000038e54000000000e037e54000000000c037e54000000000a037e540000000008037e540000000007037e540000000006037e540000000005037e540000000004037e540000000003037e54000000000e036e54000000000c036e54000000000b036e54000000000a036e540000000009036e540000000008036e540000000004036e540000000003036e540000000002036e540000000001036e540000000000036e54000000000d035e54000000000b035e54000000000a035e540000000009035e540000000008035e540000000006035e540000000005035e540000000004035e540000000003035e540000000002035e540000000001035e540000000000035e54000000000f034e54000000000e034e54000000000d034e54000000000c034e54000000000b034e54000000000a034e540000000008034e540000000004034e540000000003034e540000000002034e540000000000034e54000000000f033e54000000000e033e54000000000a033e540000000008033e540000000007033e540000000006033e540000000004033e540000000002033e540000000000033e54000000000e032e54000000000a032e540000000009032e540000000007032e540000000006032e540000000004032e540000000002032e540000000001032e540000000000032e54000000000f031e54000000000e031e54000000000d031e54000000000c031e54000000000b031e54000000000a031e540000000006031e540000000005031e540000000004031e540000000002031e540000000001031e540000000000031e54000000000d030e54000000000a030e540000000008030e540000000005030e540000000004030e540000000000030e54000000000f02fe54000000000e02fe54000000000c02fe54000000000b02fe54000000000a02fe54000000000702fe54000000000602fe54000000000402fe54000000000202fe54000000000e02ee54000000000c02ee54000000000a02ee54000000000802ee54000000000602ee54000000000402ee54000000000302ee54000000000202ee54000000000002ee54000000000f02de54000000000e02de54000000000c02de54000000000a02de54000000000902de54000000000802de54000000000502de54000000000402de54000000000302de54000000000102de54000000000002de54000000000f02ce54000000000a02ce54000000000702ce54000000000602ce54000000000502ce54000000000402ce54000000000202ce54000000000002ce54000000000e02be54000000000b02be54000000000802be54000000000602be54000000000502be54000000000402be54000000000302be54000000000202be54000000000102be54000000000002be54000000000e02ae54000000000d02ae54000000000c02ae54000000000b02ae54000000000802ae54000000000702ae54000000000402ae54000000000302ae54000000000202ae54000000000102ae54000000000002ae54000000000f029e54000000000e029e54000000000d029e54000000000c029e54000000000a029e540000000008029e540000000006029e540000000002029e540f200000000000000800595e7000000000000008c1870616e6461732e636f72652e696e64657865732e62617365948c0a5f6e65775f496e64657894939468008c05496e6465789493947d94288c0464617461948c156e756d70792e636f72652e6d756c74696172726179948c0c5f7265636f6e7374727563749493948c056e756d7079948c076e6461727261799493944b0085944301629487945294284b014b028594680a8c0564747970659493948c024f3894898887945294284b038c017c944e4e4e4affffffff4affffffff4b3f749462895d94288c057072696365948c06416d6f756e7494657494628c046e616d65944e75869452942e8d0000000000000080059582000000000000008c1870616e6461732e636f72652e696e64657865732e62617365948c0a5f6e65775f496e6465789493948c1970616e6461732e636f72652e696e64657865732e72616e6765948c0a52616e6765496e6465789493947d94288c046e616d65944e8c057374617274944b008c0473746f70944bc88c0473746570944b0175869452942e amount=0.2,time_unix=1632744259.1,price=43510.5,quote_asset_amount=8702.1,cum_ob_imbalance_lev_1=13.355802640722723,cum_ob_imbalance_lev_2=13.689135974056057,cum_ob_imbalance_lev_3=12.746635974056057,cum_ob_imbalance_lev_4=11.772635974056056,cum_ob_imbalance_lev_5=167.77263597405604,cum_ob_imbalance_lev_6=167.62263597405604,cum_ob_imbalance_lev_7=166.66166157684003,cum_ob_imbalance_lev_8=166.00532354867102,cum_ob_imbalance_lev_9=165.1155342455916,cum_ob_imbalance_lev_10=164.55509468515203,cum_ob_imbalance_lev_15=385.58929086480646,cum_ob_imbalance_lev_20=1332.4691370186524,ob_imbalance_lev_1=14.355802640722723,ob_imbalance_lev_2=1.3333333333333333,ob_imbalance_lev_3=0.0575,ob_imbalance_lev_4=0.026,ob_imbalance_lev_5=157.0,ob_imbalance_lev_6=0.8500000000000001,ob_imbalance_lev_7=0.03902560278399205,ob_imbalance_lev_8=0.3436619718309859,ob_imbalance_lev_9=0.11021069692058348,ob_imbalance_lev_10=0.43956043956043955,ob_imbalance_lev_15=163.7,ob_imbalance_lev_20=3.042666666666667,ob_usd_imbalance_lev_1=14.355637671317169,ob_usd_imbalance_lev_2=1.3332873679452706,ob_usd_imbalance_lev_3=0.0574914103168128,ob_usd_imbalance_lev_4=0.02599492094134951,ob_usd_imbalance_lev_5=156.95129433663095,ob_usd_imbalance_lev_6=0.8496679534905098,ob_usd_imbalance_lev_7=0.039008115985175736,ob_usd_imbalance_lev_8=0.3434882456503729,ob_usd_imbalance_lev_9=0.11015245204680738,ob_usd_imbalance_lev_10=0.43931299049517847,ob_usd_imbalance_lev_15=163.53827641267762,ob_usd_imbalance_lev_20=3.0392415784113274,ob_imbalance_ratio_lev_1=0.93487803774268,ob_imbalance_ratio_lev_2=0.5714285714285714,ob_imbalance_ratio_lev_3=0.05437352245862884,ob_imbalance_ratio_lev_4=0.025341130604288498,ob_imbalance_ratio_lev_5=0.9936708860759493,ob_imbalance_ratio_lev_6=0.45945945945945954,ob_imbalance_ratio_lev_7=0.037559808612440196,ob_imbalance_ratio_lev_8=0.2557651991614256,ob_imbalance_ratio_lev_9=0.09927007299270073,ob_imbalance_ratio_lev_10=0.3053435114503817,ob_imbalance_ratio_lev_15=0.9939283545840922,ob_imbalance_ratio_lev_20=0.7526385224274407,cum_ob_imbalance_ratio_lev_1=0.93487803774268,cum_ob_imbalance_ratio_lev_2=1.5063066091712514,cum_ob_imbalance_ratio_lev_3=1.5606801316298802,cum_ob_imbalance_ratio_lev_4=1.5860212622341687,cum_ob_imbalance_ratio_lev_5=2.579692148310118,cum_ob_imbalance_ratio_lev_6=3.0391516077695777,cum_ob_imbalance_ratio_lev_7=3.0767114163820177,cum_ob_imbalance_ratio_lev_8=3.3324766155434435,cum_ob_imbalance_ratio_lev_9=3.431746688536144,cum_ob_imbalance_ratio_lev_10=3.737090199986526,cum_ob_imbalance_ratio_lev_15=7.349269335985824,cum_ob_imbalance_ratio_lev_20=11.797897582698646\n";
        final int len = ilp.length();

        long mem = Unsafe.malloc(len, MemoryTag.NATIVE_DEFAULT);
        try {
            Chars.asciiStrCpy(ilp, len, mem);
            long fd = Net.socketTcp(true);
            if (fd != -1) {
                if (Net.connect(fd, Net.sockaddr("127.0.0.1", 9009)) == 0) {
                    try {
                        int sent = Net.send(fd, mem, len);
                        System.out.println("Sent " + sent + " out of " + len);
                    } finally {
                        Net.close(fd);
                    }
                } else {
                    System.out.println("could not connect");
                }
            } else {
                System.out.println("Could not open socket [errno=" + Os.errno() + "]");
            }
        } finally {
            Unsafe.free(mem, len, MemoryTag.NATIVE_DEFAULT);
        }

    }
}