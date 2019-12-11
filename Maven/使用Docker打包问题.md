- 出现问题
```
No plugin found for prefix 'docker' in the current project and in the plugin groups
```
- 解决方案
```
# 需要在maven的settings.xml中加入如下配置
<pluginGroups>
	<pluginGroup>com.spotify</pluginGroup>
</pluginGroups>
```