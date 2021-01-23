#include <stdio.h>
int putInt(int *p, int value){
	*p = value;
	printf("地址：%p的值,", p);
	printf("设置为%d\n",value);
	return value;
}
int useregister(int value){
	int register val = value;
	return val;
}
int main(int argc, char const *argv[])
{
	int tmp = 0;
	int *p = &tmp;
	putInt(p, 12);
	printf("%d\n", tmp);
	/* code */
	return tmp;
}