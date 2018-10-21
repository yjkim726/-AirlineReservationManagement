
create table Goods(
	--//private String goodsNumber;
	--//private String goodsPrice;
 	--//private String goodsDiscountWhether;
	--//private String goodsDeleteWhether;  //��ǰ��������
	--//private String goodsRestrictWhether;  //��ǰ���翩��
	--//private String goodsRegistrationDate;  //��ǰ�������
	--//private String goodSubclassCode; //��ǰ �Һз��ڵ�(fk)
	--//private String memberId; //ȸ��table ȸ��ID(fk)
	
	
	goodsNumber varchar2(20),
	goodsPrice int,
	goodsDiscountWhether varchar2(15), --��ǰ ���� ����  Goods.java���� boolean
	goodsDeleteWhether varchar2(15),
	goodsRestrictWhether varchar2(15),
	goodsRegistrationDate date,
	goodSubclassCode varchar2(15),
	memberId varchar2(20),
	constraint Goods_pk_goodsNumber primary key(goodsNumber),
	constraint Goods_fk_goodSubclassCode foreign key(goodSubclassCode) references SubclassCode,
	constraint Goods_fk_memberId foreign key(memberId) references Member
	
);