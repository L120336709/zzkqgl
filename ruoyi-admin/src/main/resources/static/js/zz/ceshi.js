function allReadyEdit() {
    var mstform = Ext.getCmp('p_form0000700039_m');
    var dgrid = Ext.getCmp('p_form0000700039_dgrid');
    var dstore = dgrid.store;

    var dgrid3 = Ext.getCmp('p_form0000700039_d2grid');
    var dstore3 = dgrid3.store;

    mstform.getItem('user_xmmc').setVisible(false);
    mstform.getItem('user_nbgyh').setVisible(false);
    mstform.getItem('user_wtzf').setVisible(false);
    mstform.getItem('user_wtzfm').setVisible(false);
    mstform.getItem('user_wtxydm').setVisible(false);
    mstform.getItem('u_nw').setVisible(false);
    mstform.getItem('textcol_2').setVisible(false);
    mstform.getItem('textcol_3').setVisible(false);
    mstform.getItem('user_yhbz').setVisible(false);
    mstform.getItem('oth_yhzh').setVisible(false);
    mstform.getItem('oth_bankname').setVisible(false);
    mstform.getItem('user_bc2').setVisible(false);
    mstform.getItem('user_jl').setVisible(false);
    mstform.getItem('nmg_bank').setVisible(false);
    mstform.getItem('nmg_yhzh').setVisible(false);
    mstform.getItem('num_2bili').setVisible(false);
    mstform.getItem('user_fjm').setVisible(false);
    mstform.getItem('user_czje').setVisible(false);
    mstform.getItem('user_bcczbl').setVisible(false);
    mstform.getItem('user_dw').setVisible(false);
    mstform.getItem('username').setVisible(false);
    mstform.getItem('user_bili2').setVisible(false);
    mstform.getItem('user_bili2').setValue(0);

    //等待所有数据加载后再执行方法
    mstform.on('dataready', function () {

        //如果时查看，则不进行任何判断，只显示录入的数据即可
        if (otype == $Otype.VIEW) {return }

        var bankname0 = "";
        var yhzh0 = "";


        //农民工账户或者委托账户，本次支付申请金额，监听金额变动情况，根据总得申请金额来判断
        mstform.getItem('user_bc2').addListener('change', function () {
            //农民工账户或者委托账户 本次支付申请金额，以下均以 本次支付申请金额 表示
            var user_bc2 = Ext.Number.from(mstform.getItem('user_bc2').getValue());
            //合同金额
            var user_htje = Ext.Number.from(mstform.getItem('user_htje').getValue());
            //本次总的申请金额
            var sqje = Ext.Number.from(mstform.getItem('user_sqje').getValue());
            //总得申请金额没填则不判断
            if (sqje == null) {
                return
            }
            //合同金额为空则表示还没有选择合同，提示先选择合同
            if (user_htje == null) {
                Ext.MessageBox.alert('提示', '请先选择合同!');
                mstform.getItem('user_bc2').setValue('');
                return
            }
            //本次支付申请金额不为空时判断，是否大于总申请金额
            if (user_bc2 != null && user_bc2 > sqje) {
                Ext.MessageBox.alert('提示', '金额不能大于本次总申请金额!');
                mstform.getItem('user_bc2').setValue('');
                return
            }
            //农民工账户时，有2个账户，第一账户金额为本次申请金额，第二账户的金额=总得申请金额-第一账户的本次申请金额
            mstform.getItem('user_jl').setValue(Ext.Number.from(sqje - user_bc2));
            //计算第二账户对应的申请比例
            mstform.getItem('num_2bili').setValue(Ext.Number.from(sqje - user_bc2) / user_htje);
            //计算本次申请金额的比例，占总得合同金额百分比
            if (user_bc2 != null && user_bc2 != '') {
                mstform.getItem('user_bili2').setValue(user_bc2 / user_htje);
            }
        });

        //获取最后一次选择的合同编码的值，用于判断当前合同是否和上次选择的合同相同
        var lasthtbm = mstform.getItem('user_htbm').getValue()
        //获取 是否是外部单位 的值，1表示是，0表示不是
        var u_nw = mstform.getItem('u_nw').getValue()
        //如果是外部单位，则把项目和合同锁定，不能选择
        if (u_nw == 1) {
            mstform.getItem('user_htbm').userSetReadOnly(true);
            mstform.getItem('pc').userSetReadOnly(true);
        }
        //数据加载完以后，判断单据体是否有数据，没有则执行ht方法，查询相关数据
        if (dstore.getCount() == 0) {
            ht() //把合同金额组成 单据体的数据进行查询和填充，并把第一个合同的本次申请金额赋值成表头中的本次申请金额
        }
        //合同金额组成单据体的编辑监听事件，如果改动了单个合同的申请金额，把他和其他合同申请金额相加，判断是否大于总得申请金额
        dgrid.addListener('edit', function (editor, e) {
            if (e.field == 'numericcol_4') {//监听每个合同的本次申请金额字段
                if (e.originalValue == e.value) {//金额没变则不操作
                    return;
                }
                //计算并判断各合同本次申请金额之和是否大于本次申请总金额
                var bcsqzje = mstform.getItem('user_sqje').getValue()//总的申请金额
                var u_sqje = 0;//加起来所有的合同审请金额
                //遍历累加 申请金额
                Ext.Array.each(dstore.data.items, function (record1) {
                    u_sqje = u_sqje + Ext.Number.from(record1.get('numericcol_4'), 0)
                });
                var record = e.record;
                if (u_sqje > bcsqzje) {
                    Ext.MessageBox.alert('提示', '申请金额之和不能大于本次申请总金额!');
                    record.set('numericcol_4', 0)
                    return;
                }
                //本次后累积申请金额=本次申请金额+累积已申请金额
                var numericcol_4 = record.get('numericcol_4')
                record.set('numericcol_6', Ext.Number.from(record.get('numericcol_5'), 0) + Ext.Number.from(numericcol_4, 0))
                //计算本次申请比例
                record.set('numericcol_1', Ext.Number.from(numericcol_4, 0) / Ext.Number.from(record.get('u_htje'), 0))
                //本次后累积申请比例=本次申请比例+累积已申请比例
                record.set('numericcol_3', record.get('numericcol_1') + record.get('numericcol_2'))
            }
        });

        //设置内部附件点击上传事件
        var dgrid4 = Ext.getCmp('p_form0000700039_d3grid');
        dgrid4.on('cellclick', function (grid, rowIndex, columnIndex, e) {
            if (columnIndex == 1) {//只有第一行点击才弹出附件
                OpenAttachment('grid', 'p_form0000700039_d3')
            }
        })

        //获取单据编号
        var bill_no = mstform.getItem('bill_no').getValue()
        if (bill_no != '') {
            var fjs = ['附件1: 发票', '附件2: 《工程进度款支付申请证书》', '附件3: 《工程款支付证书》', '附件4: 《工程款支付申请表》',
                '附件5: 《2022年7月现场完成情况说明》', '附件6: 《质量承诺书》', '附件7: 《***项目累计已完成投资造价咨询报告（xxxx年xx月）》',
                '附件8: 监理月报', '附件9: 合同及补充协议']
            execServer('gt_billtophid', {'bill_no': bill_no}, function (res2) {
                if (res2.count == 1) {
                    var arrfj = new Array();
                    var wb = 0;
                    execServer('gt_wbfjscqk', {'m_code': res2.data[0].phid}, function (res) {
                        if (res.count > 0) {
                            wb = 1;
                            for (var j = 0; j < res.count; j++) {

                                for (var fs = 0; fs < fjs.length; fs++) {
                                    if (res.data[j].textcol_1 == fjs[fs]) {

                                        if (res.data[j].textcol_2 != null) {
                                            arrfj.push({
                                                u_fjyq: fjs[fs],
                                                u_fjmc: res.data[j].textcol_2,
                                                asr_flg: 1
                                            });
                                        } else {
                                            arrfj.push({u_fjyq: fjs[fs]});
                                        }
                                    }
                                }
                            }
                        }
                    });
                    var dgrid2 = Ext.getCmp('p_form0000700039_d1grid');
                    var dstore2 = dgrid2.store;
                    dstore2.insert(dstore2.getCount(), arrfj);
                    dgrid2.on('cellclick', function (grid, rowIndex, columnIndex, e) {
                        Ext.MessageBox.alert('提示', '查看外部付款上传的附件请点击右上角 外部审核情况 跳转外部表单查看！');
                    })

                    var Toolbar = Ext.getCmp('toolbar');
                    Toolbar.insert(5, {
                        itemId: "sldtz",
                        text: "外部审核情况",
                        width: this.itemWidth,
                        iconCls: "icon-Boo" //按钮形状
                    });
                    Toolbar.get('sldtz').on('click', function () {
                        url = 'http://61.184.223.51:8000/SUP/CustomIntegrate/CustomIntegrateEdit?otype=view&hastitle=1&id=' + res2.data[0].phid + '&customBusCode=EFORM0000700038&Authorization=ngtokenkey%24WEB%241683248234%24740221110001004%2471210ef4-6841-4b56-8fd7-6b54a0e631fd&AppTitle=%E5%90%88%E5%90%8C%E4%BB%98%E6%AC%BE%E7%94%B3%E8%AF%B7(%E5%A4%96%E9%83%A8)-%E6%9F%A5%E7%9C%8B';
                        if (wb == 1) {
                            window.open(url);
                        } else {
                            Ext.MessageBox.alert('提示', '无外部对应数据！');
                        }

                    });


                }
            });
        }

        if (dstore3.getCount() == 0) {
            var pc = mstform.getItem('pc').getValue()
            if (pc != null && pc != '') {
                execServer('gt_xxjd', {'pc': pc, 'lcol_2': 0}, function (res) {
                    if (res.count > 0) {
                        var arrcz = new Array();
                        for (var j = 0; j < res.count; j++) {
                            arrcz.push({textcol_1: res.data[j].title, amt: res.data[j].user_bcwccz});
                        }
                        dstore3.removeAll()
                        dstore3.insert(dstore3.getCount(), arrcz);
                    }
                });
            }
        }


        if (otype == $Otype.ADD || otype == $Otype.EDIT) {

            //委托或者农民工选择后触发
            mstform.getItem('user_htbm').on('beforetriggerclick', function (eOp, ignoreBeforeEvent) {
                var pc = mstform.getItem('pc').getValue();
                if (Ext.isEmpty(pc)) {
                    Ext.Msg.alert('提示', '请先选择项目！');
                    return false;
                }
                mstform.getItem('user_htbm').setOutFilter({
                    pc: pc
                });
                return true;
            });


            var uhtbm = mstform.getItem('user_htbm').getValue();
            if (uhtbm == null) {
                //选择项目后清空其他基础数据
                mstform.getItem('pc').addListener('change', function () {
                    mstform.getItem('user_htbm').setValue(null);
                    mstform.getItem('title').setValue(null);

                    mstform.getItem('user_qkdw').setValue(null);
                    mstform.getItem('user_htje').setValue(null);
                    mstform.getItem('user_ljsqje').setValue(null);
                    mstform.getItem('user_czbg').setValue(null);

                    mstform.getItem('u_dqczje').setValue(null);
                    mstform.getItem('u_dqczbl').setValue(null);
                    mstform.getItem('u_dqczfkje').setValue(null);
                    mstform.getItem('u_title').setValue(null);
                    mstform.getItem('u_num_dqczje').setValue(null);

                    mstform.getItem('user_ljsqbl').setValue(null);

                    mstform.getItem('u_fkje').setValue(null);
                    mstform.getItem('u_fkbl').setValue(null);

                    Ext.getCmp('p_form0000700039_dgrid').store.removeAll()
                    Ext.getCmp('p_form0000700039_d1grid').store.removeAll()
                    Ext.getCmp('p_form0000700039_d2grid').store.removeAll()
                    //mstform.getItem('u_czbg_dq').setValue(null);

                    mstform.getItem('user_js').setValue(null);
                    mstform.getItem('user_htmc').setValue(null);
                    mstform.getItem('bankname').setValue(null);


                    bankname0 = "";
                    yhzh0 = "";

                    mstform.getItem('yhzh').setValue(null);
                    mstform.getItem('tyxydm').setValue(null);
                    mstform.getItem('user_bchje').setValue(null);


                    var pc = mstform.getItem('pc').getValue()
                    if (pc != null && pc != '') {
                        execServer('gt_xxjd', {'pc': pc, 'lcol_2': 0}, function (res) {
                            if (res.count > 0) {
                                var arrcz = new Array();
                                for (var j = 0; j < res.count; j++) {
                                    arrcz.push({textcol_1: res.data[j].title, amt: res.data[j].user_bcwccz});
                                }
                                dstore3.removeAll()
                                dstore3.insert(dstore3.getCount(), arrcz);
                            }
                        });
                    }

                });
            }


            dgrid3.addListener('edit', function (editor, e) {
                if (e.originalValue == e.value) {
                    return;
                }

                if (e.field == 'checkboxcol_1') {
                    var record = e.record;

                    //mstform.getItem('u_title').setValue(record.get('textcol_1'));
                    //mstform.getItem('u_num_dqczje').setValue(record.get('amt'));

                    mstform.getItem('u_dqczje').setValue(record.get('amt'));

                    for (i = 0; i < dstore3.getCount(); i++) {
                        if (record.get('textcol_1') != dstore3.getAt(i).get('textcol_1')) {
                            dstore3.getAt(i).set('checkboxcol_1', '')
                        }
                    }
                }
            })


            var checkpsn = 1;
            //选择合同后操作
            //var ttle=mstform.getItem ('user_htbm').getValue();


            mstform.getItem('user_htbm').addListener('change', function () {


                checkpsn = 1;
                var phid = mstform.getItem('user_htbm').getValue()


                mstform.getItem('user_sqje').setValue(null);
                mstform.getItem('u_dqczje').setValue(null);
                mstform.getItem('u_dqczbl').setValue(null);
                mstform.getItem('u_dqczfkje').setValue(null);
                mstform.getItem('user_ljsqje').setValue(null);
                mstform.getItem('user_bchje').setValue(null)

                if (phid != '' && phid != null) {

                    if (lasthtbm != mstform.getItem('user_htbm').getValue()) {
                        ht()
                        lasthtbm = mstform.getItem('user_htbm').getValue()
                    }


                    var cishu = 0;
                    execServer('nbfkcx', {'user_htbm': phid}, function (res) {
                        cishu = res.count
                        mstform.getItem('user_js').setValue(res.count);

                        mstform.getItem('user_sqpc').setValue("第" + res.count + "次");
                        for (var j = 0; j < res.count; j++) {
                            if (res.data[j].checkpsn == null) {
                                if (mstform.getItem('bill_no').getValue() != res.data[j].bill_no) {
                                    checkpsn = 0;
                                } else {
                                    checkpsn = 2;
                                }
                            }
                        }
                    });

                    if (checkpsn == 1) {
                        cishu = cishu + 1
                        mstform.getItem('user_js').setValue(res.count + 1);
                        mstform.getItem('user_sqpc').setValue("第" + (res.count + 1) + "次");
                        //选择项目后清空其他基础数据
                        mstform.getItem('pc').addListener('change', function () {
                            mstform.getItem('user_htbm').setValue(null);
                            mstform.getItem('title').setValue(null);
                            mstform.getItem('user_qkdw').setValue(null);
                            mstform.getItem('user_htje').setValue(null);
                            mstform.getItem('user_ljsqje').setValue(null);
                            mstform.getItem('user_czbg').setValue(null);

                            mstform.getItem('u_title').setValue(null);
                            mstform.getItem('u_num_dqczje').setValue(null);
                            mstform.getItem('u_dqczje').setValue(null);
                            mstform.getItem('u_dqczbl').setValue(null);
                            mstform.getItem('u_dqczfkje').setValue(null);
                            mstform.getItem('u_czbg_dq').setValue(null);
                            mstform.getItem('user_js').setValue(null);
                            mstform.getItem('user_htmc').setValue(null);
                            mstform.getItem('bankname').setValue(null);
                            mstform.getItem('user_ljsqbl').setValue(null);

                            mstform.getItem('u_fkje').setValue(null);
                            mstform.getItem('u_fkbl').setValue(null);

                            Ext.getCmp('p_form0000700039_dgrid').store.removeAll()
                            Ext.getCmp('p_form0000700039_d1grid').store.removeAll()
                            Ext.getCmp('p_form0000700039_d2grid').store.removeAll()

                            var bankname0 = "";
                            var yhzh0 = "";

                            mstform.getItem('yhzh').setValue(null);
                            mstform.getItem('tyxydm').setValue(null);
                            mstform.getItem('user_bchje').setValue(null);

                            var pc = mstform.getItem('pc').getValue()
                            if (pc != null && pc != '') {
                                execServer('gt_xxjd', {'pc': pc, 'lcol_2': 0}, function (res) {
                                    if (res.count > 0) {
                                        var arrcz = new Array();
                                        for (var j = 0; j < res.count; j++) {
                                            arrcz.push({textcol_1: res.data[j].title, amt: res.data[j].user_bcwccz});
                                        }
                                        dstore3.removeAll()
                                        dstore3.insert(dstore3.getCount(), arrcz);
                                    }
                                });
                            }
                        });
                    }
                    if (checkpsn == 2) {

                    }

                    if (checkpsn == 0) {
                        Ext.MessageBox.alert('提示', '当前合同存在未审核付款申请，不允许新增!');
                        mstform.getItem('user_htbm').setValue(null);
                        mstform.getItem('title').setValue(null);
                        mstform.getItem('pc').setValue(null);
                        mstform.getItem('user_qkdw').setValue(null);
                        mstform.getItem('user_htje').setValue(null);
                        mstform.getItem('user_ljsqje').setValue(null);
                        mstform.getItem('user_czbg').setValue(null);

                        mstform.getItem('u_dqczje').setValue(null);
                        mstform.getItem('u_dqczbl').setValue(null);
                        mstform.getItem('u_dqczfkje').setValue(null);
                        mstform.getItem('u_czbg_dq').setValue(null);
                        mstform.getItem('user_js').setValue(null);
                        mstform.getItem('user_htmc').setValue(null);
                        mstform.getItem('bankname').setValue(null);
                        bankname0 = "";
                        yhzh0 = "";
                        mstform.getItem('yhzh').setValue(null);
                        mstform.getItem('tyxydm').setValue(null);
                        mstform.getItem('user_bchje').setValue(null)
                        return
                    }

                    //查询合同信息
                    execServer('htcx', {'phid': phid}, function (res) {
                        if (res.count > 0) {
                            mstform.getItem('user_htmc').setValue(res.data[0].title);

                            if (otype == $Otype.EDIT) {

                            } else {
                                mstform.getItem('title').setValue(res.data[0].title + "（第" + cishu + "次）");
                            }


                            mstform.getItem('pc').setValue(res.data[0].phid_pc);
                            BatchBindCombox([mstform.getItem('pc')]);

                            mstform.getItem('user_qkdw').setValue(res.data[0].phid_sencomp);
                            BatchBindCombox([mstform.getItem('user_qkdw')]);

                            mstform.getItem('user_htje').setValue(res.data[0].cnt_sum_vat_fc);
                            BatchBindCombox([mstform.getItem('user_htje')]);

                            mstform.getItem('user_ljsqje').setValue(res.data[0].user_ljyspje);
                            mstform.getItem('user_ljsqbl').setValue(res.data[0].user_ljyspje / res.data[0].cnt_sum_vat_fc);


                        }
                    });

                    //获取产值报告金额
                    execServer('gt_htbmjdcz', {'user_htbm': phid}, function (res) {
                        if (res.count > 0) {
                            var num_ljcz = 0;
                            for (var j = 0; j < res.count; j++) {
                                if (res.data[j].num_ljcz > num_ljcz) {
                                    num_ljcz = res.data[j].num_ljcz
                                }
                            }
                            mstform.getItem('user_czbg').setValue(num_ljcz);
                        }
                    });

                    //获取当期产值报告金额
                    //execServer('gt_dqcz', {'user_htbm': phid}, function (res) {
                    //	if(res.count>0){
                    //		var num_ljcz =0;
                    //		for(var j=0;j<res.count;j++){
                    //				if(res.data[j].num_ljcz>num_ljcz){
                    //					num_ljcz=res.data[j].num_ljcz
                    //				}
                    //		}
                    //		mstform.getItem('u_czbg_dq').setValue(num_ljcz);
                    //	}
                    //});


                }
            });


            //计算本次金额相关数据
            mstform.getItem('user_sqje').addListener('change', function () {
                var phid = mstform.getItem('user_htbm').getValue()

                if (mstform.getItem('user_sqje').getValue() != '' && phid == '') {
                    Ext.MessageBox.alert('提示', '请先选择合同!');
                    mstform.getItem('user_sqje').setValue('');
                    mstform.getItem('u_dqczje').setValue('');
                    mstform.getItem('u_dqczbl').setValue(null);
                    mstform.getItem('u_dqczfkje').setValue(null);
                    return
                }

                var sqje = Ext.Number.from(mstform.getItem('user_sqje').getValue());
                var htje = Ext.Number.from(mstform.getItem('user_htje').getValue());
                var ljsqje = Ext.Number.from(mstform.getItem('user_ljsqje').getValue());
                if (ljsqje == null) {
                    ljsqje = 0
                }

                if (sqje + ljsqje > htje) {
                    Ext.MessageBox.alert('提示', '申请金额大于剩余可申请金额!');
                    mstform.getItem('user_sqje').setValue('');
                    mstform.getItem('u_dqczje').setValue('');
                    mstform.getItem('u_dqczbl').setValue(null);
                    mstform.getItem('u_dqczfkje').setValue(null);
                    return
                }

                mstform.getItem('user_bchje').setValue(mstform.getItem('user_sqje').getValue()
                    + mstform.getItem('user_ljsqje').getValue());

                mstform.getItem('user_bili').setValue((sqje + ljsqje) / htje);


                var user_bc2 = Ext.Number.from(mstform.getItem('user_bc2').getValue());
                if (user_bc2 != '' && user_bc2 != 0 && user_bc2 != null) {

                    mstform.getItem('user_jl').setValue(Ext.Number.from(sqje - user_bc2));
                    mstform.getItem('num_2bili').setValue(Ext.Number.from(sqje - user_bc2) / htje);

                    mstform.getItem('user_bili2').setValue(user_bc2 / htje);
                }

                //计算基数是否是产值
                var jsjs = mstform.getItem('user_jsjs').getValue();
                //if(jsjs=='产值报告金额'){
                //	var czje=Ext.Number.from(mstform.getItem('user_czje').getValue());
                //	if(czje==null){
                //		Ext.MessageBox.alert('提示', '请输入产值金额!');
                //		return
                //	}
                //	mstform.getItem('user_bcczbl').setValue(mstform.getItem('user_sqje').getValue()/czje);
                //}else{
                mstform.getItem('num_bcbli').setValue(mstform.getItem('user_sqje').getValue() / htje);
                //}

            });

            mstform.getItem('u_dqczje').addListener('change', function () {
                var u_dqczje = mstform.getItem('u_dqczje').getValue();
                var u_dqczbl = mstform.getItem('u_dqczbl').getValue();

                if (u_dqczbl != null && u_dqczbl != '') {
                    mstform.getItem('u_dqczfkje').setValue(u_dqczje * u_dqczbl);
                }

            });
            mstform.getItem('u_dqczbl').addListener('change', function () {
                var u_dqczje = mstform.getItem('u_dqczje').getValue();
                var u_dqczbl = mstform.getItem('u_dqczbl').getValue();
                if (u_dqczbl != null && u_dqczbl != '') {
                    mstform.getItem('u_dqczfkje').setValue(u_dqczje * u_dqczbl);
                }
            });

            mstform.getItem('u_fkbl').addListener('change', function () {
                var user_czbg = mstform.getItem('user_czbg').getValue();
                var u_fkbl = mstform.getItem('u_fkbl').getValue();
                if (u_fkbl != null && u_fkbl != '') {
                    mstform.getItem('u_fkje').setValue(u_fkbl * user_czbg);
                }
            });
            mstform.getItem('user_czbg').addListener('change', function () {
                var user_czbg = mstform.getItem('user_czbg').getValue();
                var u_fkbl = mstform.getItem('u_fkbl').getValue();
                if (user_czbg != null && user_czbg != '') {
                    mstform.getItem('u_fkje').setValue(u_fkbl * user_czbg);
                }
            });

            //mstform.getItem('user_czje').addListener('change', function () {
            //	var user_czje=mstform.getItem('user_czje').getValue()
            //	if(user_czje!=null){
            //		mstform.getItem('user_bcczbl').setValue(mstform.getItem('user_sqje').getValue()/user_czje);
            //	}
            //});


        }
    });

//隐藏每个合同，外部付款申请次数
    mstform.getItem('user_js').setVisible(false);


    mstform.getItem('rbcol_1').addListener('change', function () {
        var rbcol2 = mstform.getItem('rbcol_1').getChecked()[0]
        if (rbcol2 != null) {
            var code = mstform.getItem('rbcol_1').getChecked()[0].inputValue;

            mstform.getItem('user_nbgyh').setVisible(false);
            mstform.getItem('user_wtzf').setVisible(false);
            mstform.getItem('user_wtzfm').setVisible(false);
            mstform.getItem('user_wtxydm').setVisible(false);
            mstform.getItem('textcol_2').setVisible(false);
            mstform.getItem('textcol_3').setVisible(false);
            mstform.getItem('user_bc2').setVisible(false);
            mstform.getItem('user_jl').setVisible(false);
            mstform.getItem('nmg_bank').setVisible(false);
            mstform.getItem('nmg_yhzh').setVisible(false);
            mstform.getItem('num_2bili').setVisible(false);
            mstform.getItem('user_bili2').setVisible(false);
            mstform.getItem('user_bc2').setVisible(false);
            mstform.getItem('user_bili2').setVisible(false);
            mstform.getItem('user_yhbz').setVisible(false);
            mstform.getItem('oth_yhzh').setVisible(false);
            mstform.getItem('oth_bankname').setVisible(false);
            mstform.getItem('yhzh').setVisible(true);
            mstform.getItem('bankname').setVisible(true);

            //mstform.getItem ('oth_bankname').setValue(null);


            if (code == '3') {
                mstform.getItem('user_yhbz').setVisible(true);
                mstform.getItem('oth_yhzh').setVisible(true);

                mstform.getItem('bankname').setVisible(false);
                mstform.getItem('yhzh').setVisible(false);
                mstform.getItem('yhzh').setValue(null);
                mstform.getItem('bankname').setValue(null);

                mstform.getItem('user_wtzf').setValue(null);
                mstform.getItem('user_wtzfm').setValue(null);
                mstform.getItem('user_wtxydm').setValue(null);

                mstform.getItem('textcol_3').setValue(null);
                mstform.getItem('user_bc2').setValue(null);
                mstform.getItem('user_bili2').setValue(null);

                mstform.getItem('user_nbgyh').setValue(null);
                mstform.getItem('textcol_2').setValue(null);
                mstform.getItem('user_jl').setValue(null);
                mstform.getItem('num_2bili').setValue(null);
            } else if (code == '2') {
                mstform.getItem('user_nbgyh').setVisible(true);
                mstform.getItem('user_jl').setVisible(true);
                mstform.getItem('textcol_2').setVisible(true);
                mstform.getItem('user_bc2').setVisible(true);
                mstform.getItem('user_bili2').setVisible(true);

                //农民工，隐藏开户行和银行账号
                mstform.getItem('bankname').setVisible(false);
                mstform.getItem('yhzh').setVisible(false);
                mstform.getItem('yhzh').setValue(null);
                mstform.getItem('bankname').setValue(null);

                mstform.getItem('nmg_bank').setVisible(true);
                mstform.getItem('nmg_yhzh').setVisible(true);
                mstform.getItem('num_2bili').setVisible(true);


                mstform.getItem('user_wtzf').setValue(null);
                mstform.getItem('user_wtzfm').setValue(null);
                mstform.getItem('user_wtxydm').setValue(null);
                mstform.getItem('textcol_3').setValue(null);
                mstform.getItem('user_bc2').setValue(null);
                mstform.getItem('user_bili2').setValue(null);
                mstform.getItem('user_yhbz').setValue(null);
                mstform.getItem('oth_yhzh').setValue(null);
                mstform.getItem('user_yhbz').setValue(null);
                mstform.getItem('oth_yhzh').setValue(null);

            } else if (code == '1') {
                mstform.getItem('user_wtzf').setVisible(true);
                mstform.getItem('user_wtzfm').setVisible(true);
                mstform.getItem('user_wtxydm').setVisible(true);
                mstform.getItem('textcol_3').setVisible(true);
                mstform.getItem('user_bc2').setVisible(true);
                mstform.getItem('user_bili2').setVisible(true);

                mstform.getItem('user_nbgyh').setValue(null);
                mstform.getItem('textcol_2').setValue(null);
                mstform.getItem('user_jl').setValue(null);
                mstform.getItem('num_2bili').setValue(null);
                if (mstform.getItem('yhzh').getValue() == null || mstform.getItem('yhzh').getValue() == "") {
                    mstform.getItem('yhzh').setValue(yhzh0);
                    mstform.getItem('bankname').setValue(bankname0);
                }

            } else {
                mstform.getItem('user_wtzf').setValue(null);
                mstform.getItem('user_wtzfm').setValue(null);
                mstform.getItem('user_wtxydm').setValue(null);

                mstform.getItem('textcol_3').setValue(null);
                mstform.getItem('user_bc2').setValue(null);
                mstform.getItem('user_bili2').setValue(null);
                mstform.getItem('user_yhbz').setValue(null);
                mstform.getItem('oth_yhzh').setValue(null);
                mstform.getItem('user_nbgyh').setValue(null);
                mstform.getItem('textcol_2').setValue(null);
                mstform.getItem('user_jl').setValue(null);
                mstform.getItem('num_2bili').setValue(null);
                if (mstform.getItem('yhzh').getValue() == null || mstform.getItem('yhzh').getValue() == "") {
                    mstform.getItem('yhzh').setValue(yhzh0);
                    mstform.getItem('bankname').setValue(bankname0);
                }
            }
        }


    });


//供应商信息查询
    mstform.getItem('user_qkdw').addListener('change', function () {
        var gid = mstform.getItem('user_qkdw').getValue()
        if (gid != '' && gid != null) {
            execServer('gyscx', {'phid': gid}, function (res) {
                if (res.count > 0) {
                    mstform.getItem('tyxydm').setValue(res.data[0].UnisocialCredit)

                    mstform.getItem('yhzh').setValue(res.data[0].TaxAccountNo)
                    yhzh0 = res.data[0].TaxAccountNo
                    mstform.getItem('bankname').setValue(res.data[0].TaxBankName)
                    bankname0 = res.data[0].TaxBankName;
                    mstform.getItem('nmg_yhzh').setValue(res.data[0].TaxAccountNo)
                    mstform.getItem('nmg_bank').setValue(res.data[0].TaxBankName)

                    //BatchBindCombox([mstform.getItem('bank')]);
                }
            });
        }
    });


    mstform.getItem('user_yhbz').on('beforetriggerclick', function (eOp, ignoreBeforeEvent) {    //帮助窗口打开前事件
        var gysid = mstform.getItem('user_qkdw').getValue();

        if (gysid != null && gysid != '') {
            mstform.getItem('user_yhbz').setClientSqlFilter('fg3_unitaccount.ent_id= ' + gysid);
        } else {
            Ext.Msg.alert('提示', '请先选择合同！');
            return false;
        }
    });

    mstform.on('dataready', function () {

        //var user_yhbz0=mstform.getItem('user_yhbz').getValue()
        //供应商其他银行信息查询
        mstform.getItem('user_yhbz').addListener('change', function () {
            var gid = mstform.getItem('user_yhbz').getValue()

            if (gid != '' && gid != null) {
                //if(user_yhbz0!=gid){
                //user_yhbz0=gid
                execServer('gt_gysyh', {'phid': gid}, function (res) {
                    if (res.count > 0) {
                        mstform.getItem('oth_yhzh').setValue(res.data[0].saccount)
                        mstform.getItem('oth_bankname').setValue(res.data[0].bankname)
                    }
                });
                //}
            }
        });

    });


//mstform.getItem ('user_htje').setVisible(false);
    mstform.getItem('user_jsje').setVisible(false);
    mstform.getItem('user_je').setVisible(false);
    mstform.getItem('user_czbg').setVisible(false);


    mstform.getItem('u_dqczje').setVisible(false);
    mstform.getItem('u_dqczbl').setVisible(false);
    mstform.getItem('u_dqczfkje').setVisible(false);
    mstform.getItem('u_title').setVisible(false);
    mstform.getItem('u_num_dqczje').setVisible(false);

    mstform.getItem('u_fkbl').setVisible(false);
    mstform.getItem('u_fkje').setVisible(false);


    mstform.getItem('u_czbg_dq').setVisible(false);
//mstform.getItem ('user_bcczbl').setVisible(false);


//计算基数选择后触发
    mstform.getItem('user_jsjs').addListener('change', function () {
        var jsjs = mstform.getItem('user_jsjs').getValue();

        mstform.getItem('num_bcbli').setVisible(true);
        //mstform.getItem ('user_bcczbl').setVisible(false);
        //mstform.getItem ('user_czje').setVisible(false);
        //mstform.getItem ('user_czje').setValue(null);
        mstform.getItem('u_fkbl').setVisible(false);
        mstform.getItem('u_fkje').setVisible(false);
        mstform.getItem('u_fkbl').setValue(null);
        mstform.getItem('u_fkje').setValue(null);

        if (jsjs == '合同金额') {
            //mstform.getItem ('user_htje').setVisible(true);
            mstform.getItem('user_jsje').setVisible(false);
            mstform.getItem('user_je').setVisible(false);
            mstform.getItem('user_czbg').setVisible(false);
            mstform.getItem('u_dqczje').setVisible(false);
            mstform.getItem('u_dqczbl').setVisible(false);
            mstform.getItem('u_dqczfkje').setVisible(false);
            mstform.getItem('u_title').setVisible(false);
            mstform.getItem('u_num_dqczje').setVisible(false);

            mstform.getItem('u_czbg_dq').setVisible(false);
        } else if (jsjs == '结算金额') {
            mstform.getItem('user_jsje').setVisible(true);
            //mstform.getItem ('user_htje').setVisible(false);
            mstform.getItem('user_je').setVisible(false);
            mstform.getItem('user_czbg').setVisible(false);
            mstform.getItem('u_dqczje').setVisible(false);
            mstform.getItem('u_dqczbl').setVisible(false);
            mstform.getItem('u_dqczfkje').setVisible(false);
            mstform.getItem('u_title').setVisible(false);
            mstform.getItem('u_num_dqczje').setVisible(false);

            mstform.getItem('u_czbg_dq').setVisible(false);
        } else if (jsjs == '产值报告金额') {
            mstform.getItem('user_czbg').setVisible(true);
            //mstform.getItem('u_czbg_dq').setVisible(true);
            //mstform.getItem ('user_czje').setVisible(true);

            //mstform.getItem ('user_bcczbl').setVisible(true);
            //mstform.getItem ('num_bcbli').setVisible(false);
            //mstform.getItem ('user_bcczbl').setValue(0);
            //mstform.getItem ('num_bcbli').setValue(0);
            mstform.getItem('u_dqczje').setVisible(false);
            mstform.getItem('u_dqczbl').setVisible(false);
            mstform.getItem('u_dqczfkje').setVisible(false);
            mstform.getItem('u_title').setVisible(false);
            mstform.getItem('u_num_dqczje').setVisible(false);

            mstform.getItem('u_fkbl').setVisible(true);
            mstform.getItem('u_fkje').setVisible(true);

            mstform.getItem('user_jsje').setVisible(false);
            //mstform.getItem ('user_htje').setVisible(false);
            mstform.getItem('user_je').setVisible(false);
        } else if (jsjs == '产值报告金额[当期]') {
            mstform.getItem('u_dqczje').setVisible(true);
            mstform.getItem('u_dqczbl').setVisible(true);
            mstform.getItem('u_dqczfkje').setVisible(true);
            //mstform.getItem('u_title').setVisible(true);
            //mstform.getItem('u_num_dqczje').setVisible(true);


            mstform.getItem('user_je').setVisible(false);
            mstform.getItem('user_jsje').setVisible(false);
            mstform.getItem('user_czbg').setVisible(false);
            mstform.getItem('u_czbg_dq').setVisible(false);

        } else {
            mstform.getItem('user_je').setVisible(true);
            //mstform.getItem ('user_htje').setVisible(false);
            mstform.getItem('user_jsje').setVisible(false);
            mstform.getItem('user_czbg').setVisible(false);
            mstform.getItem('u_czbg_dq').setVisible(false);
            mstform.getItem('u_dqczje').setVisible(false);
            mstform.getItem('u_title').setVisible(false);
            mstform.getItem('u_num_dqczje').setVisible(false);

            mstform.getItem('u_dqczbl').setVisible(false);
            mstform.getItem('u_dqczfkje').setVisible(false);
        }
        if (jsjs != '产值报告金额') {
            var sqje = mstform.getItem('user_sqje').getValue();
            if (sqje != '') {
                mstform.getItem('user_sqje').setValue(Ext.Number.from(mstform.getItem('user_sqje').getValue()) + 1);
                mstform.getItem('user_sqje').setValue(Ext.Number.from(mstform.getItem('user_sqje').getValue()) - 1);
            }
        }

    })


}

//获取合同金额组成
function ht() {
    //获取基础信息
    var mstform = Ext.getCmp('p_form0000700039_m');
    var dgrid = Ext.getCmp('p_form0000700039_dgrid');
    var dstore = dgrid.store;

    //获取合同编码
    var phid = mstform.getItem('user_htbm').getValue()
    if (phid != '' && phid != null) {
        //获取合同协议、补充协议金额
        execServer('bcxy', {'pphid': phid}, function (res) {
            //清除掉单据体所有数据，重新录入
            dstore.removeAll()
            //存储每行的数据，最后写入单据体
            var arr = new Array();
            //遍历查询结果，将本合同所有组成金额遍历
            for (var j = 0; j < res.count; j++) {
                //获取本合同明细对应的 内部付款 中之前的累积申请金额
                var longcol = 0;//申请次数
                var bchljsqje = 0;//累积申请金额
                var numericcol_3 = 0; //累积申请比例
                //内部付款中，金额组成这个单据体中，累积金额的查询
                execServer('ljjecx', {'u_htid': res.data[j].phid}, function (res) {
                    if (res.count > 0) {
                        var rrr = 0;
                        //对比获取最后一次申请的次数，rrr表示对应次数所属数组的序号
                        for (var r = 0; r < res.count; r++) {
                            if (longcol < res.data[r].longcol_1) {
                                longcol = res.data[r].longcol_1
                                rrr = r;
                            }
                        }
                        //填充累积申请金额和累积申请比例
                        if (longcol != 0) {
                            bchljsqje = res.data[rrr].numericcol_6
                            numericcol_3 = res.data[rrr].numericcol_3
                        }
                    }
                });
                //判断longcol==0.说明之前没有对应的合同明细申请金额，设置本次为第一次，否则对应次数+1作为本次申请次数
                if (longcol == 0) {
                    longcol = 1;
                } else {
                    longcol = longcol + 1
                }
                arr.push({
                    longcol_1: longcol,
                    u_xylx: res.data[j].user_xylx,
                    u_htje: res.data[j].amt_vat_fc,
                    u_xmmc: res.data[j].item_name,
                    u_htid: res.data[j].phid,
                    numericcol_5: bchljsqje,	//最后一次的本次后申请金额，作为本次的累积申请金额
                    numericcol_2: numericcol_3	//最后一次的本次后申请比例，作为本次的累积申请比例
                });
            }
            dstore.insert(dstore.getCount(), arr);
        });

        var sqjeall = 0;//本次中，所有合同的申请金额总数，通过计算单据体中 numericcol_4本次申请金额 相加的结果，表体的数据
        Ext.Array.each(dstore.data.items, function (record) {
            sqjeall = sqjeall + Ext.Number.from(record.get('numericcol_4'), 0)
        });
        var bcsqzje0 = mstform.getItem('user_sqje').getValue()//本次总得申请金额，表头中的数据
        //如果已经填写的各合同的申请金额为0，且已经录入了本次的总的申请金额，则把第一个合同的申请金额改成表头中总得申请金额
        if (sqjeall == 0 && bcsqzje0 != null && bcsqzje0 != '') {
            var row = 1;
            Ext.Array.each(dstore.data.items, function (record) {
                if (row == 1) {
                    record.set('numericcol_4', bcsqzje0)
                    //本次后累积申请金额=本次申请金额+累积已申请金额
                    record.set('numericcol_6', Ext.Number.from(record.get('numericcol_5'), 0) + Ext.Number.from(bcsqzje0, 0))
                    //计算本次申请比例
                    record.set('numericcol_1', Ext.Number.from(bcsqzje0, 0) / Ext.Number.from(record.get('u_htje'), 0))
                    //本次后累积申请比例=本次申请比例+累积已申请比例
                    record.set('numericcol_3', record.get('numericcol_1') + record.get('numericcol_2'))
                }
                row = row + 1
            });
        }
    }
}

function setFuji() {
    var dgrid = Ext.getCmp('p_form0000700039_d3grid');
    var dstore = dgrid.store;
    var fuji = null
    var i = 1
    Ext.Array.each(dstore.data.items, function (record) {
        if (fuji == null) {
            fuji = record.get('textcol_1')
        } else {
            fuji = fuji + ';  ' + record.get('textcol_1')
        }
    });
    var mstform = Ext.getCmp('p_form0000700039_m');
    mstform.getItem('user_fjm').setValue(fuji);


}


function attachReturnExt(key, value) {
    var dgrid = Ext.getCmp('p_form0000700039_d3grid');
    var data = dgrid.getSelectionModel().getSelection();
    if (key == 'closeNG3Container') {
        if (value == '') {
            data[0].set('textcol_1', null);
        } else {
            var res = Ext.decode(value);
            var name = "";
            for (var i = 0; i < res.length; i++) {
                if (i == 0) {
                    name = res[i].asr_name
                } else {
                    name = name + "," + res[i].asr_name
                }
            }

            data[0].set('textcol_1', name);

        }
        setFuji()
    } else {
        return;
    }
}

