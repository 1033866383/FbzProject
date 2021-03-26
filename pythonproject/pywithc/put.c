//gcc -shared -Wl,-install_name,adder.so -o put.so -fPIC put.c
#include <stdio.h>
int putInt(int *p, int value){
	*p = value;
	printf("地址：%p的值,", p);
	printf("设置为%d\n",*p);
	return *p;
}
int putAddr(long addr, int value){
    int *p;
    p = (int *)(intptr_t)addr;
    *p = value;
    printf("地址：%p的值,", p);
	printf("设置为%d\n",*p);
    return *p;
}
int* change(int value){
    int *p;
    p = &value;
    return p;
}
int useregister(int value){
	int register val = value;
	return val;
}
void chengarr(int * a){
    int arr[10];
    a[0]=-123;
    printf("%d",a[0]);
    printf("success\n");
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