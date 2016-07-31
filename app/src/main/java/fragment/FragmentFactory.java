package fragment;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者: 小光哥 on 2016/7/30.
 * 邮箱: 1445037803@qq.com
 * 修订历史:
 * 描述:
 * 　　　　　　　　┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━┛┻┓━━━━┻┓
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃
 * 　　　　　　　┃　＞　　　＜　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃...　⌒　...　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　史　┃   神兽保佑,代码无bug
 * 　　　　　　　　　┃　诗　┃
 * 　　　　　　　　　┃　之　┃
 * 　　　　　　　　　┃　宠　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┗━━━┓
 * 　　　　　　　　　┃BUG天敌　　　┣┓┣┓┣┓┣┓┣┓
 * 　　　　　　　　　┃　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛
 */

/**
 * 创建所有Fragment 的工场
 */
public class FragmentFactory {

   public static BaseFragment fragments[] = {new HomeFragment(), new AppFragment(), new GameFragment(),
            new SubjectFragment(), new CategoryFragment(), new TopFragment()};

    public static Map<Integer, BaseFragment> mFragmentsMap = new HashMap<Integer, BaseFragment>();
    private static BaseFragment fragment;

    /**
     * 返回当前的 Fragment
     * @param position 哪一个Fragment
     * @return
     */
    public static BaseFragment createFragment(int position) {
        fragment = mFragmentsMap.get(position);
        if (fragment == null) {
            mFragmentsMap.put(position, fragments[position]);
            return fragments[position];
        }

            return fragment;

    }
}
